package proyecto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import proyecto.PrincipalApplication;
import proyecto.dao.PropietarioDAO;
import proyecto.dao.TrabajadorDAO;
import proyecto.model.Propietario;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class InicioDeSesionController {

    @FXML
    public RadioButton radioPropietario;
    @FXML
    public RadioButton radioTrabajador;
    @FXML
    public TextField txtUsuario;
    @FXML
    public TextField txtPassword;
    @FXML
    public Button botonRecuperarPassword;
    @FXML
    public Button botonAcceder;
    @FXML
    public ToggleGroup usuario;

    /**
     * Able la lista de Viviendas o la lista de Fincas
     * @param usuario Se le pasa el usuario introducido
     */
    @FXML
    private void abrirListas(Usuario usuario) {
        try {
            if (usuario.getClass() == Propietario.class) {
                FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("lista_viviendas.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                ListaViviendasController controller = fxmlLoader.getController();
                controller.inicializar(usuario);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Lista de propiedades");
                stage.setScene(scene);
                stage.setResizable(true);
                stage.showAndWait();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("lista_fincas.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                ListaFincaController controller = fxmlLoader.getController();
                controller.inicializar(usuario);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Lista de fincas");
                stage.setScene(scene);
                stage.setResizable(true);
                stage.showAndWait();
            }
        } catch (IOException e) {
            Utils.mostrarDialogo("Error", "Error de conexión", "No se ha podido conectar a la Base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Obtiene el usuario de los parametros introducidos
     */
    @FXML
    private void obtenerUsuario() {
        Usuario usuario = null;

        String nombre = txtUsuario.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || password.isEmpty()) {
            Utils.mostrarDialogo("Error", "Campos vacíos", "Por favor, introduce el usuario y la contraseña.", Alert.AlertType.WARNING);
        } else {
            if (radioPropietario.isSelected()) {
                try {
                    usuario = PropietarioDAO.findByNombreAndPassword(nombre, password);
                    abrirListas(usuario);
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
                }
            } else {
                try {
                    usuario = TrabajadorDAO.findByNombreAndPassword(nombre, password);
                    abrirListas(usuario);
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
                }
            }
        }
    }

    /**
     * Abre la ventana para cambiar la contraseña
     */
    @FXML
    public void abrirCambioDeContraseña(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("cambio_de_password.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cambio de Contraseña");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
    }catch (IOException e){
            Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) botonAcceder.getScene().getWindow();
        stage.close();
    }

    /**
     * Mostra informacion de la aplicacion
     * @param actionEvent Se le pasa la activacion de un boton
     */
    @FXML
    public void mostrarAcercaDe(ActionEvent actionEvent) {
        Utils.mostrarDialogo("Acerca de", "Auditoria de finca ", "Autor: Antonio Poyato Romero\nVersión: 1.0\nTecnología: JavaFX + JDBC", Alert.AlertType.INFORMATION);
    }
}
