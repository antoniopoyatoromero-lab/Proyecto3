package proyecto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CambioDePasswordController {

    public TextField txtUsuario;
    public TextField txtPassword;
    public Button botonCancelar;
    public Button botonCambiarPassword;

    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
}
