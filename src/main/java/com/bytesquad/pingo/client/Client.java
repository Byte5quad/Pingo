package com.bytesquad.pingo.client;

import java.io.*;
import java.net.*;

import com.bytesquad.pingo.model.Message;
import com.bytesquad.pingo.model.User;

public class Client {

    // TEMPORARY SERVER_ADDRESS, switch to an IP address/ non local host address.
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 6000;

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected = false;

    public void connect(User localUser) {
        try {
            this.clientSocket = new Socket(SERVER_IP, SERVER_PORT);
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
                        Message inputMessage = (Message) in.readObject();

                        // TODO: CHANGE THIS TO UPDATE THE GUI, POSSIBLY using Platform.runLater, or some through some other kind of implementation.
                        System.out.println(inputMessage);
                    }
                } catch(IOExeception | ClassNotFoundException e) {
                    System.out.println("Error reading message.");
                }
            });

            inputThread.start();

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
            isConnected = false;
            e.printStackTrace();
            System.out.println("Error sending message.");
        }
    }
}
