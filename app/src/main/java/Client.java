import java.io.*;
import java.net.*;

import model.Message;
import model.User;

public class Client {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private volatile boolean isConnected = false;
    private ChatController controller;

    public Client(ChatController controller) {
        this.controller = controller;
    }

    public void connect(User localUser, String serverIP, int port) {
        try {
            this.clientSocket = new Socket(serverIP, port);
            System.out.println("Successfully connected to the chat.");
            this.isConnected = true;

            // Creating the output stream
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.out.flush(); // flush the buffer

            // Creating the input stream
            this.in = new ObjectInputStream(clientSocket.getInputStream());

            // Send the client user's object
            out.writeObject(localUser);
            out.flush();


            // Create a new thread to read any incoming messages from other ClientHandlers.
            Thread inputThread = new Thread(() -> {
                Message inputMessage;
                try {
                    while(isConnected) {
                        inputMessage = (Message) in.readObject();

                        // CHANGED: Calls the ChatController's addReceivedMessage() to update the chat UI with the received message.
                        controller.addReceivedMessage(inputMessage);
                    }
                } catch(IOException | ClassNotFoundException e) {
                    System.out.println("Error reading message.");
                }
            });

            inputThread.setDaemon(true);
            inputThread.start();

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error with the client. ");
        }

    }

    public void disconnect() {
		try {
				in.close();
				out.close();
			if (clientSocket != null && !clientSocket.isClosed()) {
				clientSocket.close();
				isConnected = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void sendMessage(Message message) {
        // temporary code for message sending, will probably need to change for MVP
        try {
            if(isConnected) {
                out.reset();
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            isConnected = false;
            e.printStackTrace();
            System.out.println("Error sending message.");
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
