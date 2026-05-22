package com.bytesquad.pingo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML private VBox villageList;
    @FXML private StackPane contentArea;
    @FXML private Label topbarTitle;

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

        showHome();
    }

    private void handleVillageClick(String villageId, String villageName) {
        topbarTitle.setText(villageName.trim());

        Label placeholder = new Label("You selected: " + villageName.trim() + "\nCourse communities coming soon!");
        placeholder.getStyleClass().add("placeholder-label");
        placeholder.setWrapText(true);

        contentArea.getChildren().setAll(placeholder);
    }

    private void showHome() {
        Label welcome = new Label("👋 Welcome to Pingo!\nSelect a village from the sidebar to get started.");
        welcome.getStyleClass().add("placeholder-label");
        welcome.setWrapText(true);
        contentArea.getChildren().setAll(welcome);
    }
}