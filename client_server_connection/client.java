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
		//BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		try{
			String query_request = argv[2];
			//send the word to the server to be searched for 
			outToServer.writeBytes(query_request+"\n");//
		}
		catch(Exception e){
			System.out.println("Reading files failed.");
			System.exit(0);
		}


		try{
			int filesize=193;
			int bytesRead;
			int currentTot = 0;
			


			//obtain results from server
			InputStream is = clientSocket.getInputStream();
			FileOutputStream fos = new FileOutputStream("result.txt");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
	

			//getting filesize of results from server that is going to be transmitted to us
			StringBuilder sb = new StringBuilder();
			char next = (char)is.read();
			while( next!='\n'){
				sb.append(next);			
				next = (char)is.read();
			}filesize = Integer.parseInt(sb.toString());
			byte [] bytearray = new byte [filesize];
			System.out.println("File incoming: " + filesize+"bytes.");

			//save file
			bytesRead = is.read(bytearray,0,bytearray.length);
			currentTot = bytesRead;
			do {
				bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
				if(bytesRead >= 0) currentTot += bytesRead;
			} while(currentTot<filesize);
			bos.write(bytearray, 0 , currentTot);
			bos.flush(); bos.close(); 

	
		}
		catch(Exception e){
			System.out.println("TRANSFER FILE FAIL.");
			System.exit(0);
		}
		clientSocket.close();

	}
}

