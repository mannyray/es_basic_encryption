import java.io.*;
import java.net.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//arguments:
//   $1: <server_address> ~ address of server
//   $2: <n_port> ~ port number being listened by server
//   $3: <wordToSearch> ~ file containing query to be executed by elasticsearch
class client{
	public static void main(String argv[]) throws Exception
	{
		//CHECK THAT ARGUMENTS PASSED ARE VALID IN TYPE AND COUNT
		if(argv.length!=3){
			System.out.println("ERROR: INVALID ARGUMENT COUNT.");
			System.exit(0);
		}


		ServerSocket welcomeSocket = new ServerSocket(0);
		//System.out.println("SERVER_PORT="+welcomeSocket.getLocalPort());


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
			String query_request = argv[2];
			//send the word to the server
			outToServer.writeBytes(query_request+"\n"+welcomeSocket.getLocalPort()+"\n");
		}
		catch(Exception e){
			System.out.println("Reading files failed.");
			System.exit(0);
		}

		//clientSocket.close();
		//clientSocket = new Socket(argv[0], Integer.parseInt(argv[1]));
		try{


			String output="g";
			while(output!=null){
				output = inFromServer.readLine();
				System.out.println(output);
			}

			
		}
		catch(Exception e){
			System.out.println("TRANSFER FILE FAIL.");
			System.exit(0);
		}
		clientSocket.close();

	}
}

