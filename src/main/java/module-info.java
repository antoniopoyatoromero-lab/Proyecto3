module org.example.proyecto3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;


    opens proyecto to javafx.fxml;
    exports proyecto;
}