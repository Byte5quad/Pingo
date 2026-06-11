package com.bytesquad.pingo.server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {

    // Synchronized list since we are using multiple threads
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void startServer(int port) {
        Thread serverThread = new Thread(() -> {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Connected to the server.");
            while(true) {
                Socket socket = serverSocket.accept();

                // Create a new client handler for the newly created socket
                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);

                // Create a new thread for the clientHandler
                new Thread(clientHandler).start();
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
}
