import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.Arrays;
import model.Message;


public class ChatComponent extends VBox {

    private ChatController controller;
    private VBox messageContainer;
    private ScrollPane scrollPane;
    private TextField messageField;
    private Button sendButton;

    public ChatComponent(ChatController controller) {
        this.controller = controller;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #f4f4f4;");

        scrollPane = new ScrollPane();
        messageContainer = new VBox(10);
        messageContainer.setPadding(new Insets(10));

        scrollPane.setContent(messageContainer);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        HBox inputLayout = new HBox(10);
        messageField = new TextField();
        messageField.setPromptText("Type a message...");
        HBox.setHgrow(messageField, Priority.ALWAYS);

        sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white; -fx-background-radius: 5;");

        Runnable handleSendAction = () -> {
            String text = messageField.getText().trim();

            // Make sure the textbox is not empty (otherwise, don't send)
            if (!text.isEmpty() && controller != null) {
                String[] tokens = text.split(" ");

                // Checks if the message is a private message
                if(tokens.length > 0 && tokens[0].equalsIgnoreCase("/msg")) {
                    // Makes sure that the private message follows /msg [recipientID] [messageBody]
                    if(tokens.length >= 3) {
                        try {

                            // Grab the recipient ID and the message body
                            int recipientID = Integer.parseInt(tokens[1]);
                            String messageBody = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));

                            // Send message if the message body is not empty
                            if(!messageBody.isEmpty()) {
                                controller.sendChatMessage(messageBody, recipientID);
                                messageField.clear();
                            }

                        } catch (Exception e) {
                            System.out.println("Invalid private message send.");
                        }
                    }
                }

                else {
                    controller.sendChatMessage(text);
                    messageField.clear();
                }
            }
        };

        sendButton.setOnAction(e -> handleSendAction.run());
        messageField.setOnAction(e -> handleSendAction.run());

        inputLayout.getChildren().addAll(messageField, sendButton);
        this.getChildren().addAll(scrollPane, inputLayout);
    }

    public void appendMessage(String senderName, int senderID, String timestamp, String text, boolean isCurrentUser, Message.MessageType messageType) {
        Platform.runLater(() -> {
            // 1. Create the text styling for the message body
            Text messageText = new Text(text);
            TextFlow bubble = new TextFlow(messageText);
            bubble.setPadding(new Insets(8, 12, 8, 12));

            javafx.scene.layout.HBox headerRow = new javafx.scene.layout.HBox(8); // 8px gap

            Text nameText = new Text(isCurrentUser ? "You" : senderName + " (ID: " + senderID + ") ");
            nameText.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");

            Text timeText = new Text(timestamp != null ? timestamp : "");
            timeText.setStyle("-fx-font-size: 9px; -fx-fill: #7f8c8d;");
            javafx.scene.layout.HBox.setMargin(timeText, new Insets(2, 0, 0, 0));
            headerRow.getChildren().addAll(nameText, timeText);

            VBox stackedMessageBlock = new VBox(4);
            stackedMessageBlock.getChildren().addAll(headerRow, bubble);

            HBox messageRow = new HBox(stackedMessageBlock);

            switch(messageType) {
                case PUBLIC:
                    if (isCurrentUser) {
                        bubble.setStyle("-fx-background-color: #0078d4; -fx-background-radius: 10;");
                        messageText.setStyle("-fx-fill: white;");
                        headerRow.setAlignment(Pos.CENTER_RIGHT);
                        messageRow.setAlignment(Pos.CENTER_RIGHT);
                    }
                    
                    else {
                        bubble.setStyle("-fx-background-color: #e1e1e1; -fx-background-radius: 10;");
                        messageText.setStyle("-fx-fill: black;");
                        headerRow.setAlignment(Pos.CENTER_LEFT);
                    }
                    break;
                case PRIVATE:
                    bubble.setStyle("-fx-background-color: #ef820d; -fx-background-radius: 10;");
                    messageText.setStyle("-fx-fill: black;");
                    headerRow.setAlignment(Pos.CENTER_LEFT);
                    messageRow.setAlignment(Pos.CENTER_LEFT);
                    break;
            }


            messageContainer.getChildren().add(messageRow);
            Platform.runLater(() -> scrollPane.setVvalue(1.0));
        });
    }
}
