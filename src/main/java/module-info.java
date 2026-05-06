module org.example.proyecto3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens proyecto to javafx.fxml;
    exports proyecto;
}