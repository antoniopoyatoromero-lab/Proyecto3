package proyecto.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proyecto.dao.ConceptoCuotaDAO;
import proyecto.dao.CuotaDAO;
import proyecto.model.ConceptoCuota;
import proyecto.model.Cuota;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;
import proyecto.utils.Utils;

import java.util.List;

public class AddCuotaController {

    public TextField txtNombre;
    public Button botonCancelar;
    public Button botonCambiarPassword;
    public TextField labelDescripcion;
    public TextField labelCosto;
    public ComboBox<ConceptoCuota> boxConceptoCuota;
    private Inmueble inmuebleSeleccionado = null;

    /**
     * Inicia la ventana para añadir la cuota
     * @param inmueble Se le pasa el inmueble seleccionado
     */
    public void inicializar(Inmueble inmueble){
        inmuebleSeleccionado = inmueble;
        cargarConceptosCuota();
        configurarComboBoxCuota();
    }

    /**
     * Carga todos los conceptos de cuota
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
     * Configura la lista desplegable con los conceptos de cuota
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
     * Añade la cuota a la base de datos
     * @param action Se le pasa la activacion del boton
     */
    public void AddCuota(ActionEvent action){
        String texto = labelCosto.getText();
        String nombre = txtNombre.getText();
        String descripcion = labelDescripcion.getText();

        if (texto.matches("-?\\d*(\\.\\d*)?") && !texto.isEmpty() && !texto.equals("-") && !texto.equals(".")) {
            if (boxConceptoCuota.getValue() != null && !nombre.isEmpty() && !descripcion.isEmpty()) {

                double coste = Double.parseDouble(texto);
                ConceptoCuota conceptoCuota = boxConceptoCuota.getValue();
                Cuota cuota = new Cuota(nombre, descripcion, coste, conceptoCuota, inmuebleSeleccionado);
                try {
                    CuotaDAO.addCuota(cuota);
                    Utils.mostrarDialogo("Funciono", "Cuota añadido", "La cuota ha sido añadida con exito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) boxConceptoCuota.getScene().getWindow();
                    stage.close();
                } catch (RuntimeException e) {
                    Utils.mostrarDialogo("Error", "Error en la base de datos", "No se pudo añadir el concepto de cuota intentalo de nuevo", Alert.AlertType.WARNING);
                }
            } else {
                Utils.mostrarDialogo("Error", "Campos Vacío", "Completa de rellenar todos los campos", Alert.AlertType.WARNING);
            }
        }else {
            Utils.mostrarDialogo("Error", "Campos no valido", "Introduce un numero entero o uno decimal", Alert.AlertType.WARNING);
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
