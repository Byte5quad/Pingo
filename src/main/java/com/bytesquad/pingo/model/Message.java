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

    public enum MessageType {
        PUBLIC, PRIVATE
    }

    private User sender;
    private String messageContent;
    private LocalDateTime timestamp;
    private MessageType messageType;
    private int recipientId; // -1 for public messages

    public Message(User sender, String messageContent, int recipientId) {
        this.sender = sender;
        this.messageContent = messageContent;
        this.messageType = MessageType.PRIVATE;
        this.recipientId = recipientId;
        // Gets the current date/time from the system
        this.timestamp = LocalDateTime.now();
    }

    public Message(User sender, String messageContent) {
        this(sender, messageContent, -1);
        this.messageType = MessageType.PUBLIC;
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

    public MessageType getMessageType() {
        return messageType;
    }

    public int getRecipientId() {
        return recipientId;
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
