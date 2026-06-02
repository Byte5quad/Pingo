package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.model.Message;
import com.model.User;

public class Client {

    // TEMPORARY SERVER_ADDRESS, switch to an IP address/ non local host address.
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isConnected = false;

    public void connect(User clientUser) {
        try(Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("Successfully connected to the chat.");
            this.isConnected = true;

            // Creating the output stream
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush(); // flush the buffer

            // Creating the input stream
            this.in = new ObjectInputStream(socket.getInputStream());


        } catch(IOException e) {
            System.out.println("Error with the client. ");
        }

    }

    public void sendMessage(Message message) {
        // temporary code for message sending, will probably need to change for MVP
        try {
            if(isConnected) {
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Error sending message." + e.getMessage());
        }
    }
}
