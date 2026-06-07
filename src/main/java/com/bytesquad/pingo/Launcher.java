package com.bytesquad.pingo;

import javafx.application.Application;
import com.bytesquad.pingo.server.ChatServer;

public class Launcher {
    public static void main(String[] args) {
        ChatServer.startServer();
        Application.launch(HelloApplication.class, args);
    }
}
