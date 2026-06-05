module com.bytesquad.pingo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.bytesquad.pingo to javafx.fxml;
    opens com.bytesquad.pingo.model to javafx.fxml;

    exports com.bytesquad.pingo;
}
