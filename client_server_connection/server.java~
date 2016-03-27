import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/*
The server that runs on the same machine that elasticsearch is running on. The server program
acts like a wrapper that takes in incoming password-query pairs from clients. The program decrypts
the appropriate data/pointer containing files within elasticsearch and executes the query. After this 
the decrypted files are removed so that only the encrypted files are left and the result is sent
back to client.

Program is currently incomplete and cannot be relied upon to provide security.
*/

/*
arguments:
$1: <current script directory>
		the script were the program scripts are located
$2: <elasticsearch directory> 
		the directory where elasticsearch is located

		The directories have to be full 'pwd''s like locations

$3: <port_number> [optional]
    portnumber on which elasticsearch is listening on
    default value of 9200
*/
//The server assumes that the sensitive elasticsearch data is already encrypted
// before starting. Can use the encryptAllData.sh to achieve this

class server {
	static int elastic_search_port_number;
	public static void main(String argv[]) throws Exception {
		//CHECK THAT ARGUMENTS PASSED ARE VALID IN TYPE AND COUNT
		if(!(argv.length==3||argv.length==2)){
			System.out.println("ERROR: INVALID ARGUMENT COUNT. ONLY "+argv.length+" provided.");
			System.exit(0);
		}
		try {
			elastic_search_port_number = 9200;
			if(argv.length==3){//default port number
				elastic_search_port_number = Integer.parseInt(argv[2]);
			}
		}
		catch(Exception e){
			System.out.println("ERROR: INVALID ARGUMENT TYPES.");
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
			
			//Acquire key
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
			try{
				Runtime.getRuntime().exec("chmod u+x "+argv[0]+"/"+queryFileName);
				Runtime.getRuntime().exec(argv[0]+"/"+queryFileName);
			}
			catch(Exception e){
				System.out.println("QUERY FAIL.");
				System.exit(0);	
			}

			try{
				File file = new File("out.txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String availalbe;
				while((availalbe = br.readLine()) != null) {
					outToClient.writeBytes(availalbe+"\n");
				}

			}
			catch(Exception e){
				
				System.out.println("FILE TRANSFER FAIL.");
				

			}	
			connectionSocket.close();

		}
	}    
}
