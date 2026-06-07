package com.bytesquad.pingo;

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
            if (!text.isEmpty() && controller != null) {
                controller.sendChatMessage(text);
                messageField.clear();
            }
        };

        sendButton.setOnAction(e -> handleSendAction.run());
        messageField.setOnAction(e -> handleSendAction.run());

        inputLayout.getChildren().addAll(messageField, sendButton);
        this.getChildren().addAll(scrollPane, inputLayout);
    }

    public void appendMessage(String text, boolean isCurrentUser) {
        Platform.runLater(() -> {
            Text messageText = new Text(text);
            TextFlow bubble = new TextFlow(messageText);
            bubble.setPadding(new Insets(8, 12, 8, 12));

            HBox messageRow = new HBox(bubble);

            if (isCurrentUser) {
                bubble.setStyle("-fx-background-color: #0078d4; -fx-background-radius: 10;");
                messageText.setStyle("-fx-fill: white;");
                messageRow.setAlignment(Pos.CENTER_RIGHT);
            } else {
                bubble.setStyle("-fx-background-color: #e1e1e1; -fx-background-radius: 10;");
                messageText.setStyle("-fx-fill: black;");
                messageRow.setAlignment(Pos.CENTER_LEFT);
            }

            messageContainer.getChildren().add(messageRow);
            Platform.runLater(() -> scrollPane.setVvalue(1.0));
        });
    }
}
