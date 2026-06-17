import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class VillageViewController {

    private User localUser;
    private String selectedVillage;

    private ChatController currentController = null;
    private String currentDepartment = null;

    @FXML private Label villageHeaderTitle;
    @FXML private Label villageHeaderSub;
    @FXML private TilePane departmentsGrid;

    public void setLocalUser(User user) {
        this.localUser = user;
    }

    public void setVillageData(String villageName) {
        this.selectedVillage = villageName;
        villageHeaderTitle.setText(villageName);
        villageHeaderSub.setText("Welcome to the " + villageName + " hub. Select a department to view available courses.");

        departmentsGrid.getChildren().clear();

        if (villageName.contains("De Anza") || villageName.contains("Foothill")) {
            departmentsGrid.getChildren().addAll(
                createDepartmentCard("💻 Computer Science", "12 Active Courses"),
                createDepartmentCard("📐 Mathematics", "8 Active Courses"),
                createDepartmentCard("🧪 Chemistry", "6 Active Courses"),
                createDepartmentCard("🎨 Graphic Design", "4 Active Courses")
            );
        } else if (villageName.contains("Transfer")) {
            departmentsGrid.getChildren().addAll(
                createDepartmentCard("🎓 UC Transfers", "4 Communities"),
                createDepartmentCard("🏫 CSU Transfers", "3 Communities")
            );
        }
    }

    private void openChatForDepartment(String departmentName) {

        if (departmentName.equals(currentDepartment) && currentController != null) {
            return;
        }

        if (currentController != null) {
            currentController.stop();
            currentController = null;
        }

        currentDepartment = departmentName;

        villageHeaderTitle.setText(departmentName);
        villageHeaderSub.setText("Live chat channel for " + departmentName + " students.");

        departmentsGrid.getChildren().clear();

        if (this.localUser == null) {
            this.localUser = SessionManager.getInstance().getLocalUser();
        }

        if (this.localUser == null) {
            System.err.println("Error: Cannot start chat. Local user is null.");
            departmentsGrid.getChildren().add(new Label("⚠️ Please log in to view chat channels."));
            return;
        }

        ChatController controller = new ChatController(localUser, getChatRoomName(departmentName));
        currentController = controller;

        ChatComponent chatUI = controller.getChatComponent();

        chatUI.setPrefWidth(700);
        chatUI.setMinHeight(400);
        chatUI.setMaxHeight(400);

        departmentsGrid.getChildren().add(chatUI);
        controller.start();
    }

    private String getChatRoomName(String selectedDepartment) {
        return selectedVillage + "-" + selectedDepartment;
    }

    private VBox createDepartmentCard(String title, String count) {
        VBox card = new VBox();

        card.setPrefWidth(230);
        card.setPrefHeight(90);
        card.setMinHeight(90);
        card.setMaxHeight(90);

        card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        card.setStyle(
            "-fx-background-color: #f8fafc;" +
            "-fx-padding: 20;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e2e8f0;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;"
        );
        card.setSpacing(8);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1e293b;");

        Label countLabel = new Label(count);
        countLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px;");

        card.getChildren().addAll(titleLabel, countLabel);

        card.setOnMouseClicked(e -> openChatForDepartment(title));

        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: #f1f5f9;" +
            "-fx-padding: 20;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #cbd5e1;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;"
        ));

        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: #f8fafc;" +
            "-fx-padding: 20;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e2e8f0;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;"
        ));

        return card;
    }
}
