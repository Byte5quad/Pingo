package com.bytesquad.pingo;

import com.bytesquad.pingo.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import com.bytesquad.pingo.server.ChatServer;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField userIdField;
    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String userIdStr = userIdField.getText().trim();
        String ip = ipField.getText().trim();
        String portStr = portField.getText().trim();

        if (username.isEmpty() || userIdStr.isEmpty() || ip.isEmpty() || portStr.isEmpty()) {
            showError("All fields are required!");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            int port = Integer.parseInt(portStr);
            User user = new User(username, userId);
            SessionManager.getInstance().setLocalUser(user);
            SessionManager.getInstance().setServerIp(ip);
            SessionManager.getInstance().setServerPort(port);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene dashboardScene = new Scene(dashboardRoot);

            if (getClass().getResource("styles.css") != null) {
                dashboardScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            }
            stage.setScene(dashboardScene);
            stage.centerOnScreen();
            stage.show();

            // Open the server on the specified port (after login)
            ChatServer.startServer(port);

        } catch (NumberFormatException e) {
            showError("User ID and Port must be valid numbers!");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load application dashboard scene.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setManaged(true);
        errorLabel.setVisible(true);
    }
}
