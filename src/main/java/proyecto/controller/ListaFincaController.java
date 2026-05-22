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
import proyecto.dao.FincaDAO;
import proyecto.model.Finca;
import proyecto.model.Propietario;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

import java.io.IOException;
import java.util.List;

public class ListaFincaController {

    public ListView <Finca> listaFincas;
    public Label labelId;
    public Label labelDireccion;
    public Button botonAdministrarViviendas;
    public Button botonAddConcepto;
    private Usuario trabajadorEscogido;

    /**
     * Inicia la interfaz
     * @param usuario Se le pasa el usuario
     */
    @FXML
    public void inicializar(Usuario usuario) {
        trabajadorEscogido = usuario ;
        configurarLista();
        cargarFincas();
    }

    /**
     * Abre la lista de viviendas gestionadas por un trabajador
     * @param actionEvent Se le pasa la activacion del boton
     */
    @FXML
    public void abrirListaViviendasTrabajador(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("lista_viviendas_trabajador.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ListaViviendasTrabajadorController controller = fxmlLoader.getController();
            controller.inicializar(listaFincas.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Lista de propiedades");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.showAndWait();
        } catch (IOException e) {
            Utils.mostrarDialogo("Error", "Error de conexión", "No se ha podido conectar a la Base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre la interfaz para añadir un concepto de cuota
     */
    @FXML
    public void abrirAddConceptoCuota(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("add_concepto_cuota.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddConceptoCuotaController controller = fxmlLoader.getController();
            controller.inicializar(listaFincas.getSelectionModel().getSelectedItem());
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
     * Abre la interfaz para borrar el concepto de cuota
     */
    @FXML
    public void abrirBorrarConceptoCuota(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("borrar_concepto_cuota.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            BorrarConceptoCuotaController controller = fxmlLoader.getController();
            controller.inicializar();
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
     * Configura la lista de las fincas
     */
    private void configurarLista() {
        listaFincas.setCellFactory(listView -> new ListCell<Finca>() {
            @Override
            protected void updateItem(Finca finca, boolean empty) {
                super.updateItem(finca, empty);

                if (empty || finca == null) {
                    setText(null);
                    return;
                }

                setText(finca.getDireccion());
            }
        });

        listaFincas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarFinca((Finca) newValue);
        });
        listaFincas.setPlaceholder(new Label("No hay libros para mostrar"));
        botonAdministrarViviendas.disableProperty().bind(listaFincas.getSelectionModel().selectedItemProperty().isNull());
        botonAddConcepto.disableProperty().bind(listaFincas.getSelectionModel().selectedItemProperty().isNull());

    }

    /**
     * Carga los datos de las fincas
     */
    private void cargarFincas() {
        List<Finca> fincas = FincaDAO.findByIdTrabajador(trabajadorEscogido.getId());
        listaFincas.setItems(FXCollections.observableList(fincas));

    }

    /**
     * Gestiona como se muestra la finca
     * @param finca Se le pasa la finca que se quiere mostrar
     */
    private void mostrarFinca(Finca finca) {
        if (finca != null) {
            labelId.setText(String.valueOf(finca.getId()));
            labelDireccion.setText(finca.getDireccion());
        } else {
            labelId.setText(null);
            labelDireccion.setText(null);
        }

    }

    /**
     * Recarga las Fincas
     * @param actionEvent Se le pasa la activacion del boton
     */
    public void recargar(ActionEvent actionEvent){
        cargarFincas();
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
