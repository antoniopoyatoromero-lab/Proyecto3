module org.example.proyecto3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires javafx.base;
    requires org.example.proyecto3;


    opens proyecto to javafx.fxml;
    exports proyecto;

    opens proyecto.dataAccess to java.xml.bind;
    exports proyecto.dataAccess;
    exports proyecto.controller;
    opens proyecto.controller to javafx.fxml;
}