package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.model.Message;

public class ClientServer {

    // TEMPORARY SERVER_ADDRESS, switch to an IP address/ non local host address.
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void main(String[] args) {
        try(Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("Successfully connected to the chat.");

            // Creating the input and output streams for the message objects
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Message testingMessage = new Message("Ryan", "This is a testing message for sockets." );
            sendMessage(testingMessage);

        } catch(IOException e) {
            System.out.println("Error with the client. ");
        }

    }

    public void sendMessage(Message message) {
        // temporary code for message sending, will probably need to change for MVP
        try {
            out.writeObject(message);
            // REMOVE LATER
            System.out.println("DEBUG: Message sent.");
        } catch (IOException e) {
            System.out.println("Error sending message. ");
        }
    }
}
