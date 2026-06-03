package com.server;

import com.model.User;
import com.model.Message;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private List<ClientHandler> clients;
    private Socket socket;
    private User clientUser;

    public ClientHandler(Socket socket, List<ClientHandler> clientsList) {
        this.socket = socket;       // Holds the socket created by ChatServer
        this.clients = clientsList; // Holds the ArrayList containing all ClientHandlers.
    }

    // run() is automatically run when a new thread is created in ChatServer.
    @Override
    public void run() {
        try {

            // Create new object and input streams for communication (conceptually, ClientHandler is the Server "endpoint");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());

            // Read the User object sent when a Client connects to a port.
            clientUser = (User) in.readObject();

            // TODO: Read messages sent from the Client. 

            // compiler complains without the ClassNotFoundException being caught
        } catch(IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
      sendMessage(Message message)
      Description: Sends a message back to the client (!!!).
      Different from sendMessage in Client.java as that sends a message to ClientHandler (basically the server)
     */
    public void sendMessage(Message message) {

        try {
                out.writeObject(message);
                out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message.");
        }
    }

    /*
    TODO: sendMessageToAll(Message message)
    Description: Does a for-each loop through each clientHandler in clients, and sends a message back to the Client.
    */


}