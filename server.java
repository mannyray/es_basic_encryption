import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		System.out.println("SERVER_PORT="+welcomeSocket.getLocalPort());

    	//always listening for a new client, processing one client at a time
		while(true){
			//TCP portion: Negotiation
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			//Acquire key
			String clientCodeKey = inFromClient.readLine();
			System.out.println("clientCode="+clientCodeKey);
			
			//Acquire query
			String query = "";
			System.out.println("query=");
			try{
				String line = null;
				while((line=inFromClient.readLine())!=null){
					if(line.equals("{!}QUERY_FINISHED{!}")){
						break;
					}
					System.out.println(line);
					query+=(line+"\n");
				}
			}
			catch(Exception e){
				System.out.println("Query parse error.");
			}

			//write query to file to be executed once file is decrypted
			String queryFileName = "";
			try {
				Writer writer = null;
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss:ms");
				Date date = new Date();
				queryFileName = "query";//dateFormat.format(date);System.out.println(queryFileName);
				writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(queryFileName), "utf-8"));
				writer.write(query);writer.close();
			}
			catch (IOException ex) {
  				System.out.println("Failure in writing query to file.");
			}

			//decrypt all appropriate files
			try{
				ProcessBuilder pb = new ProcessBuilder(argv[0]+"/decrypt.sh",clientCodeKey);
				Process p = pb.start();     
				p.waitFor();                
				System.out.println("Decrypt script executed successfully");
			}
			catch(Exception e){
				System.out.println("DECRYPT FAIL.");
				System.exit(0);			
			}

			//execute query
			try{
				ProcessBuilder pb = new ProcessBuilder(argv[0]+"/"+queryFileName);
				Process p = pb.start();    
			  	p.waitFor();               
				System.out.println("Query script executed successfully");
			}
			catch(Exception e){
				System.out.println("QUERY FAIL.");
				System.exit(0);	
			}

			//encrypt
			try{
				ProcessBuilder pb = new ProcessBuilder(argv[0]+"removeUnecrypted.sh",argv[1]);
				Process p = pb.start();   
				p.waitFor();               
				System.out.println("Encrypt script executed successfully");
			}
			catch(Exception e){
				System.out.println("ENCRYPT FAIL.");
				System.exit(0);					
			}

			connectionSocket.close();
		}
	}    
}
