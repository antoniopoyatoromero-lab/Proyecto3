package proyecto.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import proyecto.PrincipalApplication;
import proyecto.dao.InmuebleDAO;
import proyecto.model.Finca;
import proyecto.model.Inmueble;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

import java.io.IOException;
import java.util.List;

public class ListaViviendasTrabajadorController {
    public ListView<Inmueble> listaPropiedades;
    public Label labelId;
    public Label labelDireccion;
    public Label labelValor;
    public Label labelIdPropietario;
    public Button botonAddCuota;
    public Finca fincaEscogida = null;
    public Button botonEditarPropietario;

    /**
     * Se inicia la interfaz
     * @param finca Se le pasa la finca seleccionada
     */
    @FXML
    public void inicializar(Finca finca) {
        fincaEscogida = finca;
        configurarLista();
        cargarInmuebles();
    }

    /**
     * Se abre la ventana para añadir la cuota
     */
    @FXML
    public void abrirAddCuota(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("add_cuota.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            AddCuotaController controller = fxmlLoader.getController();
            controller.inicializar(listaPropiedades.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir un Concepto de Cuota");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (IOException e){
            Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Se abre la ventana para editar el propietario
     */
    @FXML
    public void abrirEditarPropietario(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("editar_propietario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            EditarPropietarioController controller = fxmlLoader.getController();
            controller.inicializar(listaPropiedades.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Propietario");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (IOException e){
            Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Se configura la lista
     */
    private void configurarLista() {
        listaPropiedades.setCellFactory(listView -> new ListCell<Inmueble>() {
            @Override
            protected void updateItem(Inmueble inmueble, boolean empty) {
                super.updateItem(inmueble, empty);

                if (empty || inmueble == null) {
                    setText(null);
                    return;
                }

                setText(inmueble.getDireccion());
            }
        });

        listaPropiedades.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarInmuebles((Inmueble) newValue);
        });
        listaPropiedades.setPlaceholder(new Label("No hay propiedades para mostrar"));
        botonAddCuota.disableProperty().bind(listaPropiedades.getSelectionModel().selectedItemProperty().isNull());
        botonEditarPropietario.disableProperty().bind(listaPropiedades.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Se carga los inmuebles
     */
    private void cargarInmuebles() {
        List<Inmueble> inmuebles = InmuebleDAO.findByIdFinca(fincaEscogida.getId());
        listaPropiedades.setItems(FXCollections.observableList(inmuebles));

    }

    /**
     * Se muestra el inmueble seleccionado a la lista
     * @param inmueble Se muestra el inmueble seleccionado
     */
    private void mostrarInmuebles(Inmueble inmueble) {
        if (inmueble != null) {
            labelId.setText(String.valueOf(inmueble.getId()));
            labelDireccion.setText(inmueble.getDireccion());
            labelValor.setText(String.valueOf(inmueble.getCoste()));
            labelIdPropietario.setText(String.valueOf(inmueble.getPropietario().getNombre()));
        } else {
            labelId.setText(null);
            labelDireccion.setText(null);
            labelValor.setText(null);
            labelIdPropietario.setText(null);
        }

    }

    /**
     * Se recarga los inmuebles
     * @param actionEvent Se le pasa a la activacion del boton
     */
    public void recargar(ActionEvent actionEvent){
        cargarInmuebles();
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) labelId.getScene().getWindow();
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
