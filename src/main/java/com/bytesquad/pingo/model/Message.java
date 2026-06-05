package com.bytesquad.pingo.model;

// serializable needed so that we can pass the Message object
// to the socket in an object output stream

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private User sender;
    private String messageContent;
    private LocalDateTime timestamp;

    public Message(User sender, String messageContent) {
        this.sender = sender;
        this.messageContent = messageContent;

        // Gets the current date/time from the system
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public User getSender() {
        return sender;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Formatting for the LocalDateTime
    public String getFormattedTime() {

        DateTimeFormatter customFormatDate = DateTimeFormatter.ofPattern("'['MM-dd-yyyy HH:mm:ss']'");

		return timestamp.format(customFormatDate);
    }

    @Override
    public String toString() {
        return getFormattedTime() + " " + this.sender.getName() + ": " + messageContent;
    }
}
