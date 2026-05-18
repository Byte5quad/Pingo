package com.model;

// serializable needed so that we can pass the Message object
// to the socket in an object output stream

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String messageContent;
    private LocalDateTime timestamp;

    public Message(String sender, String messageContent) {
        this.sender = sender;
        this.messageContent = messageContent;

        // Gets the current date/time from the system
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String messageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    
}
