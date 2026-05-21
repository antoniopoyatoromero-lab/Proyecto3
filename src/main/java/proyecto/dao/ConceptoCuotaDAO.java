package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.ConceptoCuota;
import proyecto.model.Finca;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConceptoCuotaDAO {
    private final static String SQL_ALL = "SELECT * FROM conceptoCuota";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM ConceptoCuota Where idconceptoCuota = ?";
    private final static String SQL_INSERT = "INSERT INTO ConceptoCuota (tipo,idFinca) VALUES (?,?)";
    private final static String SQL_DELETE = "DELETE FROM ConceptoCuota WHERE idconceptoCuota = ?";

    /**
     * Busca en la base de datos todas los conceptos de cuota de la base de datos
     * @return Devuelve todos los conceptos de cuota que recoge de la base de datos
     */
    public static List<ConceptoCuota> findAll() {
        List<ConceptoCuota> conceptoCuotas = new ArrayList<>();
        try (ResultSet rs = ConnectionDB.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("idconceptoCuota");
                String tipo = rs.getString("tipo");
                ConceptoCuota conceptoCuota = new ConceptoCuota(id,tipo);
                conceptoCuotas.add(conceptoCuota);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conceptoCuotas;
    }

    /**
     * Busca en la base de datos un concepto de cuota por la id de el concepto de cuota
     * @param id Le paso el id del concepto de cuota
     * @return Devuelve el concepto de cuota  que se ha encontrado de la base de datos
     */
    public static ConceptoCuota findById(int id){
        ConceptoCuota conceptoCuota = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                conceptoCuota = new ConceptoCuota(id,tipo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conceptoCuota;
    }

    /**
     * Añada a la base de datos el concepto de cuota creado
     * @param conceptoCuota Se pasa el concepto de cuota creado
     */
    public static void addConceptoCuota(ConceptoCuota conceptoCuota) {
        if (!conceptoCuota.getTipo().isEmpty()) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, conceptoCuota.getTipo());
                ps.setInt(2, conceptoCuota.getFinca().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *Borra el concepto de cuota seleccionado
     * @param id Se le pasa la id de el concepto de cuota que se quiere borrar
     */
    public static void deleteConceptoCuotaById(int id) {
        if(findById(id)!=null){
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
