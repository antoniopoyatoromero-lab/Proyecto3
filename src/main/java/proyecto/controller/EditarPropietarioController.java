package proyecto.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import proyecto.dao.ConceptoCuotaDAO;
import proyecto.dao.CuotaDAO;
import proyecto.dao.InmuebleDAO;
import proyecto.dao.PropietarioDAO;
import proyecto.model.ConceptoCuota;
import proyecto.model.Cuota;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;
import proyecto.utils.Utils;

import java.util.List;

public class EditarPropietarioController {

    public ComboBox<Propietario> boxPropietarios;
    Inmueble inmuebleSeleccionado = null;

    /**
     * Inicializa la interfaz para editar un propietario de la vivienda
     * @param inmueble Se le pasa el inmueble seleccionado
     */
    public void inicializar(Inmueble inmueble){
        inmuebleSeleccionado = inmueble;
        cargarPropietarios();
        configurarComboBoxPropietario();
    }

    /**
     * Carga los propietarios
     */
    private void cargarPropietarios() {
        try {
            List<Propietario> propietarios = PropietarioDAO.findAll();
            propietarios.sort((a1, a2) -> a1.getNombre().compareTo(a2.getNombre()));
            boxPropietarios.setItems(FXCollections.observableArrayList(propietarios));
        } catch (RuntimeException e) {
            Utils.mostrarDialogo("Error", "Error al cargar", "No se han podido obtener los conceptos.", Alert.AlertType.ERROR);
        }

    }

    /**
     * Configura la lista despegable
     */
    private void configurarComboBoxPropietario() {
        boxPropietarios.setCellFactory(listView -> new ListCell<Propietario>() {
            @Override
            protected void updateItem(Propietario propietario, boolean empty) {
                super.updateItem(propietario, empty);
                setText(empty || propietario == null ? null : propietario.getNombre());
            }
        });

        boxPropietarios.setButtonCell(new ListCell<Propietario>() {
            @Override
            protected void updateItem(Propietario propietario, boolean empty) {
                super.updateItem(propietario, empty);
                setText(empty || propietario == null ? null : propietario.getNombre());
            }
        });
    }

    /**
     * Cambia el propietario de una vivienda
     * @param actionEvent Se le pasa la activacion de un boton
     */
    @FXML
    private void cambiarPropietario(ActionEvent actionEvent){
        if (boxPropietarios.getValue() != null) {
            Propietario propietario = boxPropietarios.getValue();
            if (propietario.getId() == inmuebleSeleccionado.getPropietario().getId()) {
                Utils.mostrarDialogo("Error", "Valor erroneo", "No se puede eligir el mismo propietario", Alert.AlertType.WARNING);
            } else {
                try {
                    InmuebleDAO.updatePropietario(propietario.getId(), inmuebleSeleccionado.getId());
                    Utils.mostrarDialogo("Funciono", "Cuota añadido", "La cuota ha sido añadida con exito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) boxPropietarios.getScene().getWindow();
                    stage.close();
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Error en la base de datos", "No se pudo añadir el concepto de cuota intentalo de nuevo", Alert.AlertType.WARNING);
                }
            }
        }else{
            Utils.mostrarDialogo("Error", "Campo vacio", "Selecciona algun propietario", Alert.AlertType.WARNING);
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) boxPropietarios.getScene().getWindow();
        stage.close();
    }

}
