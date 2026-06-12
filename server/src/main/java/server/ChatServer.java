package server;

import model.Message;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {

    // Synchronized list since we are using multiple threads
    private static Map<Integer, ClientHandler> clients = Collections.synchronizedMap(new HashMap<>());

    public static void startServer(int port) {
        Thread serverThread = new Thread(() -> {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Connected to the server.");
            while(true) {
                Socket socket = serverSocket.accept();

                // Create a new thread for the clientHandler
                new Thread(new ClientHandler(socket)).start();
            }
        } catch(IOException e) {
            System.out.println("Error creating server.");
            e.printStackTrace();
        }
    }, "ChatServer"); // for debugging
    // Sets the server thread as a background thread.
    serverThread.setDaemon(true);
    // Starts the server thread.
    serverThread.start();
    }

    public static void registerClient(int userId, ClientHandler clientHandler) {
        clients.put(userId, clientHandler);
    }

    public static void removeClient(int userId) {
        clients.remove(userId);
    }

    public static ClientHandler getClient(int userId) {
        return clients.get(userId);
    }

    public static List<ClientHandler> getClients() {
        return clients.values().stream().toList();
    }

    public static void broadcastToAll(Message message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients.values()) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }
}
