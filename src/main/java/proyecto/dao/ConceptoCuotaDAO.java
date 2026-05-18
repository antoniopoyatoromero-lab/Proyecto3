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
    private final static String SQL_FIND_BY_ID = "SELECT * FROM ConceptoCuota Where idconceptoCuota = ?";
    private final static String SQL_FIND_BY_ID_FINCA = "SELECT * FROM ConceptoCuota where idFinca =?";
    private final static String SQL_INSERT = "INSERT INTO ConceptoCuota (tipo,idFinca) VALUES (?,?)";
    private final static String SQL_DELETE = "DELETE FROM ConceptoCuota WHERE idconceptoCuota = ?";

    public static ConceptoCuota findById(int id){
        ConceptoCuota conceptoCuota = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                conceptoCuota = new ConceptoCuota(id,tipo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conceptoCuota;
    }

    public static List<ConceptoCuota> findByIdFinca(int idFinca){
        ArrayList<ConceptoCuota> conceptoCuotas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_FINCA)) {
            ps.setInt(1, idFinca);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                int id = rs.getInt("idConceptoCuota");
                String tipo = rs.getString("tipo");

                ConceptoCuota conceptoCuota = new ConceptoCuota(id,tipo);
                conceptoCuotas.add(conceptoCuota);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conceptoCuotas;
    }

    public static boolean addFinca(ConceptoCuota conceptoCuota) {
        boolean added = false;
        if (conceptoCuota != null && findById(conceptoCuota.getId()) == null) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, conceptoCuota.getTipo());
                ps.setInt(2, conceptoCuota.getFinca().getId());
                ps.executeUpdate();

                added = true;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean deleteConceptoCuotaById(int id) {
        boolean deleted = false;
        if(findById(id)!=null){
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
