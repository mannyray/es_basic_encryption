import java.io.*;
import java.net.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//arguments:
//   $1: <server_address> ~ address of server
//   $2: <n_port> ~ port number being listened by server
//   $3: <key> ~ file containing password
//   $4: <query> ~ file containing query to be executed by elasticsearch
class client{
	public static void main(String argv[]) throws Exception
	{
		//CHECK THAT ARGUMENTS PASSED ARE VALID IN TYPE AND COUNT
		if(argv.length!=4){
			System.out.println("ERROR: INVALID ARGUMENT COUNT.");
			System.exit(0);
		}

		Socket clientSocket = null;
		try{

			clientSocket = new Socket(argv[0], Integer.parseInt(argv[1]));
		}
		catch(Exception e){
			System.out.println("ERROR: INVALID SERVER ADDRESS.");
			System.exit(0);			
		}
		
		//////////////
		//TCP portion: Negotiation            
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		try{
			//read in the password
			File file = new File(argv[2]);
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String query_request = br.readLine()+"\n";
	
			//read in the query
			file = new File(argv[3]);
			fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while((line=br.readLine())!=null){
				query_request+=(line+"\n");
			}

			//send the concatenation of password and query to server
			outToServer.writeBytes(query_request+"{!}QUERY_FINISHED{!}\n");
		}
		catch(Exception e){
			System.out.println("Reading files failed.");
			System.exit(0);
		}

		//obtain port number
		int portNumber = 0;
		try{
			portNumber  = Integer.parseInt(inFromServer.readLine());
		}
		catch(Exception e){
			System.out.println("ERROR: INVALID req_code.");
			System.exit(0);
		}
		clientSocket.close();
	}
}

