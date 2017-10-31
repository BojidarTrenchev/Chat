import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
	
	private Socket socket;
	private ArrayList<PrintWriter> allPrintWriters;
	private int writerIndex;
	
	public ServerThread(Socket socket, ArrayList<PrintWriter> allPrintWriters, int writerIndex){
		this.socket = socket;
		this.allPrintWriters = allPrintWriters;
		this.writerIndex = writerIndex;
	}
	
	public void run() {		
		String message = "";
		
		try {
			BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Print the name of the user who joined the chat
			String joinedMessage = socketReader.readLine() + " joined the chat.";
			sendMessage(joinedMessage);
			System.out.println(joinedMessage);
			
			//Receive and send messages
			while(message != null) {
				
				//Gets the message from the user
				message = socketReader.readLine();
				
				sendMessage(message);

				//Prints the message in the server console
				System.out.println(message);
			}
			socket.close();
		}
		catch (IOException e) {
			System.out.print("Something went wrong!");
		}
		
	}
	
	//Prints the message enter by the user using this socket to all other users
	private void sendMessage(String message) {
		for(int i = 0; i < allPrintWriters.size(); i++) {
			if(i != writerIndex) {
				allPrintWriters.get(i).println(message);
			}					
		}
	}

}
