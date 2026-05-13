module org.example.proyecto3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires org.example.proyecto3;


    opens proyecto to javafx.fxml;
    exports proyecto;

    opens proyecto.dataAccess to java.xml.bind;
    exports proyecto.dataAccess;
}