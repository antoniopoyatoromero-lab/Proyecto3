package proyecto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proyecto.dao.PropietarioDAO;
import proyecto.dao.TrabajadorDAO;
import proyecto.model.Usuario;
import proyecto.utils.Utils;



public class CambioDePasswordController {

    public TextField txtUsuario;
    public TextField txtPassword;
    public Button botonCancelar;
    public Button botonCambiarPassword;
    public RadioButton radioPropietario;
    public ToggleGroup usuario;
    public RadioButton radioTrabajador;

    /**
     * Cambia la contraseña de un usuario
     */
    @FXML
    private void cambiarPassword() {
        Usuario usuario = null;

        String nombre = txtUsuario.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || password.isEmpty()) {
            Utils.mostrarDialogo("Error", "Campos vacíos", "Por favor, introduce el usuario y la contraseña.", Alert.AlertType.WARNING);
        } else {
            if (radioPropietario.isSelected()){
                try {
                    usuario = PropietarioDAO.findByNombre(nombre);
                    PropietarioDAO.updatePassword(password,usuario.getId());
                    Utils.mostrarDialogo("Funciono","Contraseña cambiada","La contraseña ha sido cambiada con exito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) txtUsuario.getScene().getWindow();
                    stage.close();
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
                }
            }else {
                try {
                    usuario = TrabajadorDAO.findByNombre(nombre);
                    TrabajadorDAO.updatePassword(password,usuario.getId());
                    Utils.mostrarDialogo("Funciono","Contraseña cambiada","La contraseña ha sido cambiada con exito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) txtUsuario.getScene().getWindow();
                    stage.close();
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
                }
            }
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
}
