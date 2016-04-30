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
		BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
			int filesize=0;
			int bytesRead;
			int currentTot = 0;
			


			//obtain results from server
			byte [] bytearray = new byte [filesize];
			InputStream is = clientSocket.getInputStream();
			FileOutputStream fos = new FileOutputStream("result.txt");
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			//getting filesize of results from server that is going to be transmitted to us
			String output = "";
			output = inFromServer.readLine();
			filesize =  Integer.parseInt(output);
			System.out.println("File size " + filesize+"bytes incoming");



			bytesRead = is.read(bytearray,0,bytearray.length);
			currentTot = bytesRead;

			do {
				bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
				if(bytesRead >= 0) currentTot += bytesRead;
				System.out.println(currentTot);
				if(currentTot==-1){
					//break;
				} 
			} while(currentTot<filesize);
			bos.write(bytearray, 0 , currentTot);
			bos.flush(); bos.close(); 

			outToServer.writeBytes("done\n");
	
		}
		catch(Exception e){
			System.out.println("TRANSFER FILE FAIL.");
			System.exit(0);
		}
		clientSocket.close();

	}
}

