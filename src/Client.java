import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket socket;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client cl = new Client();
		cl.run();
	}
	
	private void run() throws IOException {		
		socket = new Socket("127.0.0.1", 4444);
		
		BufferedReader readerFromCMD = new BufferedReader(new InputStreamReader(System.in));		
		PrintWriter writerToServer = new PrintWriter(socket.getOutputStream(), true);
		
		//Get user credentials and send them to the server
		System.out.println("Enter name: ");
		String name = readerFromCMD.readLine();
		writerToServer.println(name);
		
		System.out.println("Start chatting now!!!");
		System.out.println();
		
		//Starts the server listener
		ServerListener listener = new ServerListener();
		listener.start();
		
		String message = "";
		
		//Gets the input from the user and sends it to the server
		while (!message.equals(")end")) {
			message = readerFromCMD.readLine();
			writerToServer.println(name + ": " + message);
		}
		
		socket.close();
	}
	
	//Additional class which prints the input from the server on a different thread
	private class ServerListener extends Thread{
		public void run() {
			try {
				BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true) {
					System.out.println(readerFromServer.readLine());
				}
			} catch (IOException e) {
				System.out.print("Something went wrong!");
			}

		}
	}
}
