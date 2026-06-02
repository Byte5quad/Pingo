module com.bytesquad.pingo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bytesquad.pingo to javafx.fxml;
    exports com.bytesquad.pingo;
}