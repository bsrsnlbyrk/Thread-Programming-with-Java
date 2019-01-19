import java.io.*;
import java.net.*;
import java.util.*;

public class MultiEchoServer {
	private static ServerSocket serverSocket;
	private static final int PORT=8001;
	
	public static void main(String[] args) {
		
		try {
			serverSocket=new ServerSocket(PORT);
			System.out.println("Port opened.");
		}
		catch(IOException ioEx) {
			System.out.println(ioEx.getMessage());
			System.out.println("Port could not open!");
			System.exit(1);
		}
		do {
			try{
				Socket client=serverSocket.accept();
				System.out.println("New client connection request is accepted.");
				
				ClientHandler handler=new ClientHandler(client);
				handler.start();
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}while(true);
	}

}

class ClientHandler extends Thread{
	
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	
	
	public ClientHandler(Socket socket) {
		
		client=socket;
		
		try {
			input=new Scanner(client.getInputStream());
			output=new PrintWriter(client.getOutputStream(),true);
		}
		catch(IOException ioEx){
			ioEx.printStackTrace();
		}
	
	}
	
	public void run() {
		String received;
		
		do {
			received=input.nextLine();
			output.println("ECHO:"+received);
		}while(!received.equals("exit"));
		
		try {
			if(client!=null) {
				System.out.println("Connection is closed...");
				client.close();
			}
		}
		catch(IOException ioEx) {
			System.out.println("Connection could not close!");
		}
	}
}
