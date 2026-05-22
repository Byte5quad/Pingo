package com.model;

// serializable needed so that we can pass the Message object
// to the socket in an object output stream

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    @Serial
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

    // Formatting for the LocalDateTime
    public String getFormattedTime() {

        DateTimeFormatter customFormatDate = DateTimeFormatter.ofPattern("'['MM-dd-yyyy HH:mm:ss']'");

        String formattedDateTime = timestamp.format(customFormatDate);

        return formattedDateTime;
    }

    public String toString() {
        return getFormattedTime() + " " + this.sender + ": " + messageContent;
    }
}
