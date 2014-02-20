package client;
//Client Side
import java.io.*;
import java.net.*;

public class ClientSocket {

	int serverPort;
	InetAddress host;
	Socket socket;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	public ClientSocket(int port){
		
		try {
			serverPort = port;
			host = InetAddress.getByName("localhost"); 
			
			socket = new Socket(host,serverPort); 

			 toServer = 
				new PrintWriter(socket.getOutputStream(),true);
			fromServer = 
				new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
		}
		catch(UnknownHostException ex) {
			ex.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void send(String send) {
		System.out.println("<client> Envoi : "+send);
		toServer.println(send);
		
	}
	
	public String receiv(){
		
		String line=null;
		try {
			line = fromServer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("<client> Recoi : "+line);
		return line;
		
	}
	
	public void close(){
		System.out.println("<client> fermeture connection");
		toServer.close();
		try {
			
			fromServer.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}