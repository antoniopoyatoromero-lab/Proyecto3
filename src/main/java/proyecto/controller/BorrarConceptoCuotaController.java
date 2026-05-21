package proyecto.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import proyecto.dao.ConceptoCuotaDAO;
import proyecto.model.ConceptoCuota;
import proyecto.utils.Utils;

import java.util.List;

public class BorrarConceptoCuotaController {
    public ComboBox<ConceptoCuota> boxConceptoCuota;

    /**
     * Inicializa la aplicacion para borrar el concepto de cuota
     */
    public void inicializar(){
        cargarConceptosCuota();
        configurarComboBoxCuota();
    }

    /**
     * Carga los conceptos de cuota
     */
    private void cargarConceptosCuota() {
        try {
            List<ConceptoCuota> conceptoCuotas = ConceptoCuotaDAO.findAll();
            conceptoCuotas.sort((a1, a2) -> Integer.compare(a1.getId(),a2.getId()));
            boxConceptoCuota.setItems(FXCollections.observableArrayList(conceptoCuotas));
        } catch (RuntimeException e) {
            Utils.mostrarDialogo("Error", "Error al cargar", "No se han podido obtener los conceptos.", Alert.AlertType.ERROR);
        }

    }

    /**
     * Configura la lista despeglable
     */
    private void configurarComboBoxCuota() {
        boxConceptoCuota.setCellFactory(listView -> new ListCell<ConceptoCuota>() {
            @Override
            protected void updateItem(ConceptoCuota conceptoCuota, boolean empty) {
                super.updateItem(conceptoCuota, empty);
                setText(empty || conceptoCuota == null ? null : conceptoCuota.getTipo());
            }
        });

        boxConceptoCuota.setButtonCell(new ListCell<ConceptoCuota>() {
            @Override
            protected void updateItem(ConceptoCuota conceptoCuota, boolean empty) {
                super.updateItem(conceptoCuota, empty);
                setText(empty || conceptoCuota == null ? null : conceptoCuota.getTipo());
            }
        });
    }

    /**
     * Bora el concepto de cuota
     * @param action Se le pasa la accion del boton
     */
    public void borrarConceptoCuota(ActionEvent action){
        if (boxConceptoCuota.getValue() != null){
            ConceptoCuota conceptoCuota = boxConceptoCuota.getValue();
            try {
                ConceptoCuotaDAO.deleteConceptoCuotaById(conceptoCuota.getId());
                Utils.mostrarDialogo("Funciono","Concepto de Cuota borrado","El concepto de cuota ha sido borrado con éxito", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) boxConceptoCuota.getScene().getWindow();
                stage.close();
            } catch (RuntimeException e) {
                Utils.mostrarDialogo("Error", "Error en la base de datos", "No se pudo añadir el concepto de cuota intentalo de nuevo", Alert.AlertType.WARNING);
            }
        }else{
            Utils.mostrarDialogo("Error", "Campos Vacío", "No has seleccionado ningún campo", Alert.AlertType.WARNING);
        }
    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) boxConceptoCuota.getScene().getWindow();
        stage.close();
    }
}
