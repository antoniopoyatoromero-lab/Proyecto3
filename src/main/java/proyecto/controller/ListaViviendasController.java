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
import proyecto.model.Inmueble;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

import java.io.IOException;
import java.util.List;

public class ListaViviendasController {

    public ListView<Inmueble> listaPropiedades;
    public Button botonPagarCuotas;
    public Button botonAddDinero;
    public Button botonRecargar;
    public Button botonAtras;
    public Label labelId;
    public Label labelDireccion;
    public Label labelValor;
    private Usuario propietarioEscogido;

    /**
     * Inicializa la ventana
     * @param usuario Se le pasa el usuario seleccionado
     */
    @FXML
    public void inicializar(Usuario usuario) {
        propietarioEscogido = usuario ;
        configurarLista();
        cargarInmuebles();
    }

    /**
     * Configura la lista
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
        botonPagarCuotas.disableProperty().bind(listaPropiedades.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     *Abre la interfaz para añadir dinero
     */
    @FXML
    public void abrirAddDinero(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("add_dinero.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddDineroController controller = fxmlLoader.getController();
            controller.inicializar(propietarioEscogido);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir dinero");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (IOException e){
            Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Abre la interfaz para pagar las cuotas
     */
    @FXML
    public void abrirPagarCuotas(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("pagar_cuotas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            PagarCuotasController controller = fxmlLoader.getController();
            controller.inicializar(listaPropiedades.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir dinero");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (IOException e){
            Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Carga la base de datos a la lista
     */
    private void cargarInmuebles() {
        List<Inmueble> inmuebles = InmuebleDAO.findByIdPropietario(propietarioEscogido.getId());
        listaPropiedades.setItems(FXCollections.observableList(inmuebles));

    }

    /**
     * Muestra el inmueble seleccionado
     * @param inmueble Se le pasa el inmueble seleccinado
     */
    private void mostrarInmuebles(Inmueble inmueble) {
        if (inmueble != null) {
            labelId.setText(String.valueOf(inmueble.getId()));
            labelDireccion.setText(inmueble.getDireccion());
            labelValor.setText(String.valueOf(inmueble.getCoste()));
        } else {
            labelId.setText(null);
            labelDireccion.setText(null);
            labelValor.setText(null);
        }

    }

    /**
     * Recaerga Los inmuebles
     * @param actionEvent Se le pasa el activacion del boton
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
        Stage stage = (Stage) botonAtras.getScene().getWindow();
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
