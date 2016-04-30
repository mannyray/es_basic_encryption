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
		//send query to for our wrapper to handle
		ServerSocket welcomeSocket = new ServerSocket(0);
		int PORT = welcomeSocket.getLocalPort();
		System.out.println("SERVER_PORT="+welcomeSocket.getLocalPort());

		int clientNumber=1;
    //always listening for a new client, processing one client at a time
		while(true){
			//TCP portion: Negotiation
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			System.out.println("Serving client #"+clientNumber);clientNumber++;

			//Acquire searchword
			String query = inFromClient.readLine();
			System.out.println("seachWord="+query);

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
				writer.write("curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{\"from\" : 0, \"size\" : 10000,\"query\":{\"match\":{\"content\":\""+query+"\"}}}' > out.txt");
				writer.close();
			}
			catch (IOException ex) {
  				System.out.println("Failure in writing query to file.");
			}
			
			//executing query
			try{//execute search query
				Process p = Runtime.getRuntime().exec("chmod u+x "+argv[0]+"/"+queryFileName);//make the query executable
				p.waitFor();
				p = Runtime.getRuntime().exec(argv[0]+"/"+queryFileName);//run the query
				p.waitFor();//important to wait for query to finish executing before transmitting file 
			}
			catch(Exception e){
				System.out.println("QUERY FAIL.");
				System.exit(0);	
			}


			try{
				//query result is saved in out.txt. Read the results back to client
				File transferFile = new File ("out.txt");//TODO:out.txt
				//tell client file length to be transmitted: limit of file size based on MAX_INT
				outToClient.writeBytes((int)transferFile.length()+"\n");

				//transmit file
				byte [] bytearray = new byte [(int)transferFile.length()];
				FileInputStream fin = new FileInputStream(transferFile);
				BufferedInputStream bin = new BufferedInputStream(fin);
				bin.read(bytearray,0,bytearray.length);
				OutputStream os = connectionSocket.getOutputStream(); 
				System.out.println("Sending Files...");
				System.out.println("File size: "+bytearray.length);
				os.write(bytearray,0,bytearray.length);
				os.flush(); 
				System.out.println("File transfer complete");
			}
			catch(Exception e){
				System.out.println("FILE TRANSFER FAIL.");
			}	
			System.out.println("");
			connectionSocket.close();
		}
	}    
}
