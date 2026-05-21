package proyecto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import proyecto.dao.PropietarioDAO;
import proyecto.dao.TrabajadorDAO;
import proyecto.model.Propietario;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

import java.util.function.UnaryOperator;

public class AddDineroController {
    public Button botonConfirmar;
    public TextField txtDinero;
    private Propietario propietarioSeleccionado;

    /**
     * Incicia la interfaz para añadir dinero
     * @param usuario Se le pasa el usuario seleccionado
     */
    public void inicializar(Usuario usuario){
        propietarioSeleccionado = (Propietario) usuario;
    }

    /**
     * Se añade el dinero a la base de datos
     */
    @FXML
    private void addDinero() {
        String texto = txtDinero.getText();

        if (texto.matches("-?\\d*(\\.\\d*)?") && !texto.isEmpty() && !texto.equals("-") && !texto.equals(".")) {
            double dinero = Double.parseDouble(texto) + propietarioSeleccionado.getDinero() ;
            try {
                PropietarioDAO.updateDinero(dinero,propietarioSeleccionado.getId());
                Utils.mostrarDialogo("Funciono","Dinero actualizado","El dinero ha sido actualizado con exito", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) botonConfirmar.getScene().getWindow();
                stage.close();
            } catch (RuntimeException e) {
                Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
            }
        }else {
            Utils.mostrarDialogo("Error", "Campo inválido", "Por favor, introduce un numero entero o decimal .", Alert.AlertType.WARNING);
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) botonConfirmar.getScene().getWindow();
        stage.close();
    }
}
