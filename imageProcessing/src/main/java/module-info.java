module com.example.imageprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;


    opens com.example.imageprocessing to javafx.fxml;
    exports com.example.imageprocessing;
}