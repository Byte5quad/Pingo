package server;

import model.User;
import model.Message;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private User clientUser;

	public ClientHandler(Socket socket) {
        this.socket = socket;       // Holds the socket created by ChatServer
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

            ChatServer.registerClient(clientUser.getId(), this);

            //Broadcast join to all clients
            ChatServer.broadcastToAll(
                    new Message(clientUser, clientUser.getName() + " has joined a chat room.", null),
                    this);

            //Read messages sent from this client and send it to all other clients using sendMessageToAll.
			Message message;
			while ((message = (Message) in.readObject()) != null) {
                switch (message.getType()) {
                    case PUBLIC -> ChatServer.broadcastToAll(message, this);
                    case PRIVATE -> sendPrivateMessage(message);
                    default -> throw new IOException("Unknown message type: " + message.getType());
                }
                out.flush();
            }

            // compiler complains without the ClassNotFoundException being caught
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected: " +
                    (clientUser != null ? clientUser.getName() : "unknown"));
		} finally {
            if (clientUser != null) {
                ChatServer.removeClient(clientUser.getId());
                ChatServer.broadcastToAll(
                        new Message(clientUser, clientUser.getName() + " had left the chat", null),
                        this);
            }

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
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
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error sending to " +
                    (clientUser != null ? clientUser.getName() : "unknown"));
        }
    }

    public void sendPrivateMessage(Message message) {
        ClientHandler recipient = ChatServer.getClient(message.getRecipientId());

        if (recipient != null) {
            recipient.sendMessage(message);
            this.sendMessage(message);
        } else {
            // Send a System private message back to the client/sender
            this.sendMessage(
                    new Message(
                            new User("System", -1),
                            "User is not online or does not exist", clientUser.getId()));
        }
    }

    public User getClientUser() {
        return clientUser;
    }

}
