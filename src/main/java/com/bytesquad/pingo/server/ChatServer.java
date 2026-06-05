package com.bytesquad.pingo.server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 6000;

    // Synchronized list since we are using multiple threads
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
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
    }
}