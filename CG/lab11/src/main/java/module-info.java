module com.example.lab11 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab11 to javafx.fxml;
    exports com.example.lab11;
}