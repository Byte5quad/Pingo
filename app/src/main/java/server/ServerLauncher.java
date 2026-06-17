package server;

public class ServerLauncher {
	public static void main(String[] args) {
		System.out.println("Starting the server...");

		ChatServer.startServer(1234);

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			System.out.println("Server shutting down");
		}
	}
}
