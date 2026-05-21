package proyecto.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import proyecto.dao.CuotaDAO;
import proyecto.dao.PropietarioDAO;
import proyecto.model.Cuota;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;
import proyecto.utils.Utils;

import java.util.List;

public class PagarCuotasController {

    public ComboBox<Cuota> boxCuota;
    private Inmueble inmuebleSeleccionado = null;

    /**
     * Se inicia la interfaz
     * @param inmueble Se le pasa el inmueble seleccionado
     */
    public void inicializar(Inmueble inmueble){
        inmuebleSeleccionado = inmueble;
        cargarCuotas();
        configurarComboBoxCuota();
    }

    /**
     * Carga las cuotas
     */
    private void cargarCuotas() {
        try {
            List<Cuota> cuotas = CuotaDAO.findByIdInmueble(inmuebleSeleccionado.getId());
            cuotas.sort((a1, a2) -> Integer.compare(a1.getId(),a2.getId()));
            boxCuota.setItems(FXCollections.observableArrayList(cuotas));
        } catch (RuntimeException e) {
            Utils.mostrarDialogo("Error", "Error al cargar", "No se han podido obtener las cuotas.", Alert.AlertType.ERROR);
        }

    }

    /**
     * Se configura la lista desplegable
     */
    private void configurarComboBoxCuota() {
        boxCuota.setCellFactory(listView -> new ListCell<Cuota>() {
            @Override
            protected void updateItem(Cuota cuota, boolean empty) {
                super.updateItem(cuota, empty);
                setText(empty || cuota == null ? null : cuota.getNombre());
            }
        });

        boxCuota.setButtonCell(new ListCell<Cuota>() {
            @Override
            protected void updateItem(Cuota cuota, boolean empty) {
                super.updateItem(cuota, empty);
                setText(empty || cuota == null ? null : cuota.getNombre());
            }
        });
    }

    /**
     * Se cobra la couta
     * @param actionEvent Se le pasa la activacion del boton
     */
    @FXML
    public void pagarDinero (ActionEvent actionEvent) {
        if (boxCuota.getValue() != null) {
            Cuota cuota = boxCuota.getValue();
            if (cuota.getCosto() > inmuebleSeleccionado.getPropietario().getDinero()) {
                Utils.mostrarDialogo("Error", "Dinero Insuficiente", "No tienes dinero suficiente añade mas dinero", Alert.AlertType.WARNING);
            } else {
                double dinero = (inmuebleSeleccionado.getPropietario().getDinero() - cuota.getCosto());
                try {
                    PropietarioDAO.updateDinero(dinero, inmuebleSeleccionado.getPropietario().getId());
                    CuotaDAO.deleteCuotaById(cuota.getId());
                    Utils.mostrarDialogo("Funciono", "Cuota pagada", "La cuota se ha pagado con exito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) boxCuota.getScene().getWindow();
                    stage.close();
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Campos incorrecto", "Usuario o contraseña no encontrados intenta de nuevo", Alert.AlertType.WARNING);
                }
            }
        }else {
            Utils.mostrarDialogo("Error", "Campo vacio", "Selecciona una cuota a pagar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) boxCuota.getScene().getWindow();
        stage.close();
    }
}
