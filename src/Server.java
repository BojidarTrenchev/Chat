import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	
	private ArrayList<PrintWriter> allPrintWriters = new ArrayList<PrintWriter>();
	
	public static void main(String[] args) throws IOException {
		Server s = new Server();
		s.run();
	}
	
	public void run() throws IOException {
		ServerSocket servSocket = new ServerSocket(4444);		
		System.out.println("Server up and running...");
		
		while (true) {
			Socket socket = servSocket.accept();
			allPrintWriters.add(new PrintWriter(socket.getOutputStream(), true));
			ServerThread thread = new ServerThread(socket, allPrintWriters, allPrintWriters.size() - 1);
			
			thread.start();
		}	
	}
}
