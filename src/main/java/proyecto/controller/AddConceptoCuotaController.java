package proyecto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.dao.ConceptoCuotaDAO;
import proyecto.dao.PropietarioDAO;
import proyecto.model.ConceptoCuota;
import proyecto.model.Finca;
import proyecto.model.Usuario;
import proyecto.utils.Utils;

public class AddConceptoCuotaController {
    public TextField txtConceptoCuota;
    private Finca fincaSeleccionada;

    /**
     * Inicial la interfaz para añadir el concepto de cuota
     * @param finca Se le pasa la finca que se selecciono antes
     */
    public void inicializar(Finca finca){
        fincaSeleccionada = finca;
    }

    /**
     * Añade el concepto de cuota a la base de datos
     */
    @FXML
    private void addConceptoCuota() {
        String tipo = txtConceptoCuota.getText();
        if (!tipo.isEmpty()){
            ConceptoCuota conceptoCuota = new ConceptoCuota(tipo,fincaSeleccionada);
            try {
               ConceptoCuotaDAO.addConceptoCuota(conceptoCuota);
                Utils.mostrarDialogo("Funciono","Concepto de Cuota añadido","El concepto de cuota  ha sido añadido con éxito", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) txtConceptoCuota.getScene().getWindow();
                stage.close();
            } catch (RuntimeException e) {
                Utils.mostrarDialogo("Error", "Error en la base de datos", "No se pudo añadir el concepto de cuota intentalo de nuevo", Alert.AlertType.WARNING);
            }
        }else{
            Utils.mostrarDialogo("Error", "Campos Vacío", "El campo esta vació introdúcelo", Alert.AlertType.WARNING);
        }

    }

    /**
     * Cierra la ventana
     * @param actionEvent Se le pasa la accion de un boton
     */
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) txtConceptoCuota.getScene().getWindow();
        stage.close();
    }
}
