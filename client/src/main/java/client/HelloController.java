package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class HelloController {

    @FXML private VBox villageList;
    @FXML private StackPane contentArea;
    @FXML private Label topbarTitle;
    @FXML private Label profileNameLabel; 
    @FXML private Label profileSubLabel;

    private String[] villages = {"🏫  De Anza Village", "🎓  Foothill Village", "✈️  Transfer Village"};
    private String[] villageIds = {"deanza", "foothill", "transfer"};

    @FXML
    public void initialize() {
        for (int i = 0; i < villages.length; i++) {
            final String villageId = villageIds[i];
            final String villageName = villages[i];

            HBox item = new HBox();
            item.getStyleClass().add("sidebar-item");

            Label label = new Label(villageName);
            label.getStyleClass().add("sidebar-item-label");

            item.getChildren().add(label);
            item.setOnMouseClicked(e -> handleVillageClick(villageId, villageName));
            villageList.getChildren().add(item);
        }
        SessionManager session = SessionManager.getInstance();
        if (session.getLocalUser() != null && profileNameLabel != null) {
            profileNameLabel.setText(session.getLocalUser().getName());
            if (profileSubLabel != null) {
                profileSubLabel.setText("ID: " + session.getLocalUser().getId());
            }
        }

        showHome();
    }

    private void handleVillageClick(String villageId, String villageName) {
        String cleanName = villageName.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
        topbarTitle.setText(cleanName);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("village-view.fxml"));
            Parent villageView = loader.load();

            VillageViewController controller = loader.getController();
            controller.setVillageData(cleanName);

            // Set up the local user data fromsession manager
            SessionManager session = SessionManager.getInstance();
            if (session.getLocalUser() != null) {
                controller.setLocalUser(session.getLocalUser());
            }

            contentArea.getChildren().setAll(villageView);

        } catch (IOException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading view for " + cleanName);
            contentArea.getChildren().setAll(errorLabel);
        }
    }

    private void showHome() {
        Label welcome = new Label("👋 Welcome to Pingo!\nSelect a village from the sidebar to get started.");
        welcome.getStyleClass().add("placeholder-label");
        welcome.setWrapText(true);
        contentArea.getChildren().setAll(welcome);
    }
}
