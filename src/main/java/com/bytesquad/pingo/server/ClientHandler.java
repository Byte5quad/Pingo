package com.bytesquad.pingo.server;

import com.bytesquad.pingo.model.User;
import com.bytesquad.pingo.model.Message;

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

            //Broadcast join to all clients
            // TODO: Have this announcement be broadcast by a "Server" User
            sendMessageToAll(new Message(clientUser, " has joined the chat"));

            //Read messages sent from this client and send it to all other clients using sendMessageToAll.
			Message message;
			while ((message = (Message) in.readObject()) != null) {
                sendMessageToAll(message);
                out.flush();
            }

            // compiler complains without the ClassNotFoundException being caught
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            clients.remove(this);
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
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
            System.out.println("Error sending message.");
        }
    }

    /*
    sendMessageToAll(Message message)
    Description: Does a for-each loop through each clientHandler in clients, and sends a message to all other clients
                 connected to the server (apart from the local clientHandler).
    */
    public void sendMessageToAll(Message message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.sendMessage(message);
                }
            }
        }
    }


}
