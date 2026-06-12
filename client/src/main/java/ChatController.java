import model.User;
import model.Message;

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
        // Create a new SessionManager object with all of the local user data.
        SessionManager session = SessionManager.getInstance();

        // Get the user inputted IP and port number.
        String serverIP = session.getServerIp();
        int serverPort = session.getServerPort();
        // Call the Client's connect() method to create the client socket and start listening for messages.
        localClient.connect(localUser, serverIP, serverPort);
    }

    /*
      addReceivedMessage(Message message)
      A helper method used in Client.javathat appends a received message from another client to the local chat UI
    */
    public void addReceivedMessage(Message message) {
        // Add received messages from other clients to the chat UI.
        boolean isMessageLocal = message.getSender().equals(localUser);
        // added this to get the sender's name and timestamp for display in the chat UI
        String senderName = message.getSender().getName();
        String timestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        chatUI.appendMessage(senderName, timestamp, message.getMessageContent(), isMessageLocal);
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
        String currentTimestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        chatUI.appendMessage(localUser.getName(), currentTimestamp, messageText, true);
    }

    public void sendPrivateMessage(String messageText, int recipientId) {
        Message message = new Message(localUser, messageText, recipientId);
        localClient.sendMessage(message);
    }

    public User getLocalUser() { return this.localUser; }
    public ChatComponent getChatComponent() { return this.chatUI; }
    public Client getLocalClient() { return this.localClient; }
}
