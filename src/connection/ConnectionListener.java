package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConnectionListener extends Thread {

	private int port;

	public ConnectionListener(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket listener;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss  dd-MM-yyyy");
		try {
			listener = new ServerSocket(port);
			System.out.println("Server stared!\nListeninig on port: " + port);
			try {
				while (true) {
					Socket socket = listener.accept();
					LocalDate userLoginTime = LocalDate.now();
					
					try {
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String username = input.readLine();
						System.out.println("LOGGED: " + username);
						
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println(userLoginTime.format(dateFormat));
					} finally {
						socket.close();
					}
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
	}

}
