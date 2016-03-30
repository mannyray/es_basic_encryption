import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/*
The server that runs on the same machine that elasticsearch is running on. The server program
acts like a wrapper that takes in incoming query messages from client. The program executes
the search query and then sends the result back to the client.
*/

/*
arguments:
$1: <current script directory>
		this script were the program scripts are located
		used for executing search query
*/

class server {
	public static void main(String argv[]) throws Exception {
		//CHECK THAT ARGUMENTS PASSED ARE VALID IN TYPE AND COUNT
		if(!(argv.length==1)){
			System.out.println("ERROR: INVALID ARGUMENT COUNT. ONLY "+argv.length+" provided.");
			System.exit(0);
		}

		//get a free port and print the port number. This will be the port that the client will
		//send the key and query to for our wrapper to handle
		ServerSocket welcomeSocket = new ServerSocket(0);
		int PORT = welcomeSocket.getLocalPort();
		System.out.println("SERVER_PORT="+welcomeSocket.getLocalPort());

    //always listening for a new client, processing one client at a time
		while(true){
			//TCP portion: Negotiation
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			//Acquire searchword
			String query = inFromClient.readLine();
			int port = Integer.parseInt(inFromClient.readLine());
			System.out.println("seachWord="+query);
			System.out.println("client_port="+port);

			//write query to file to be executed 
			String queryFileName = "";
			try {
				Writer writer = null;
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss:ms");
				Date date = new Date();
				queryFileName = "query.sh";
				writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(queryFileName), "utf-8"));
				writer.write("#!/bin/bash\n");
				writer.write("curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{\"query\":{\"match\":{\"content\":\""+query+"\"}}}' > out.txt");
				writer.close();
			}
			catch (IOException ex) {
  				System.out.println("Failure in writing query to file.");
			}
			
			//execute query
			try{//execute search query
				Runtime.getRuntime().exec("chmod u+x "+argv[0]+"/"+queryFileName);
				Runtime.getRuntime().exec(argv[0]+"/"+queryFileName);
			}
			catch(Exception e){
				System.out.println("QUERY FAIL.");
				System.exit(0);	
			}

			try{//query result is saved in out.txt. Read the results back to client
				File file = new File("out.txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String availalbe;
				while((availalbe = br.readLine()) != null) {
					outToClient.writeBytes(availalbe+"\n");
				}
				outToClient.writeBytes("$$$$$$$$$$Finish");//terminating sequence
			}
			catch(Exception e){
				System.out.println("FILE TRANSFER FAIL.");
			}	
			connectionSocket.close();
		}
	}    
}
