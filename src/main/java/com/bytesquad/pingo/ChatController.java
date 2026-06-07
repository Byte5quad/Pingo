package com.bytesquad.pingo;

import com.bytesquad.pingo.client.Client;
import com.bytesquad.pingo.model.User;
import com.bytesquad.pingo.model.Message;
import com.bytesquad.pingo.server.ChatServer;

public class ChatController {
    private User localUser;
    private ChatComponent chatUI;
    private Client localClient;

    public ChatController(User localUser) {
        this.localUser = localUser;
        // Create a new ChatComponent, pass in this ChatController object.
        this.chatUI = new ChatComponent(this);
        // Call the Client constructor, and pass in this instance of ChatController
        this.localClient = new Client(this);
    }

    /*
      start()
      Starts the chat by connecting the local client to the server. Used in VillageViewController.java.
    */
    public void start() {
        // Call the Client's connect() method to create the client socket and start listening for messages.
        localClient.connect(localUser);
    }

    /*
      addReceivedMessage(Message message)
      A helper method used in Client.javathat appends a received message from another client to the local chat UI
    */
    public void addReceivedMessage(Message message) {
        // Add received messages from other clients to the chat UI.
        boolean isFromLocalUser = message.getSender().equals(localUser);

        String display;
        switch (message.getMessageType()) {
            case PRIVATE -> {
                if (isFromLocalUser) {
                    String sender = ChatServer.getClient(message.getRecipientId()).getClientUser().getName();
                    display = "[DM -> " + sender + "] " + message.getMessageContent();
                } else {
                    display = "[DM from " + message.getSender().getName() + "] " + message.getMessageContent();
                }
            }
            case PUBLIC -> display = message.getSender().getName() + ": " + message.getMessageContent();
            default -> throw new IllegalArgumentException("Invalid message type");
        }
        chatUI.appendMessage(display, isFromLocalUser);
    }


    /*
      sendChatMessage(String messageText)
      A helper method used in ChatComponent.java that appends a received message from another client to the local chat UI and sends the message to the server.
    */
    public void sendChatMessage(String messageText) {
        // Create a new Message object with the local user as the sender, and the text as the content.
        Message message = new Message(localUser, messageText);

        // Send the message to the server (other handlers) and append to local UI.
        localClient.sendMessage(message);
        chatUI.appendMessage(messageText, true);
    }

    public void sendPrivateMessage(String messageText, int recipientId) {
        Message message = new Message(localUser, messageText, recipientId);
        localClient.sendMessage(message);
    }

    public User getLocalUser() { return this.localUser; }
    public ChatComponent getChatComponent() { return this.chatUI; }
    public Client getLocalClient() { return this.localClient; }
}
