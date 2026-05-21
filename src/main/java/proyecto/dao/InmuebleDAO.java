package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Cuota;
import proyecto.model.Finca;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InmuebleDAO {

    private final static String SQL_FIND_BY_ID_FINCA = "SELECT * FROM Inmueble where idFinca =?";
    private final static String SQL_FIND_BY_ID_PROPIETARIO = "SELECT * FROM Inmueble where idPropietario =?";
    private final static String SQL_UPDATE_PROPIETARIO = "UPDATE Inmueble SET idPropietario = ? WHERE idInmueble = ?";

    /**
     * Busca la lista de inmueble relacionadas a una finca
     * @param idFinca Se le pasa el id de la finca
     * @return Devuelve la lista de los inmuebles relacionadas con la finca
     */
    public static List<Inmueble> findByIdFinca(int idFinca){
        ArrayList<Inmueble> inmuebles = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_FINCA)) {
            ps.setInt(1, idFinca);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idInmueble");
                String direccion = rs.getString("direccion");
                double coste  = rs.getDouble("valor");
                Propietario propietario = PropietarioDAO.findById(rs.getInt("idPropietario"));
                Inmueble inmueble = new Inmueble(coste,direccion,id,propietario);
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmuebles;
    }

    /**
     * Busca una lista de inmuebles relacionadas con un propetario
     * @param idPropietario Se le pasa el id del propetario
     * @return Devuelve la lista de inmuebles
     */
    public static List<Inmueble> findByIdPropietario(int idPropietario){
        ArrayList<Inmueble> inmuebles = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_PROPIETARIO)) {
            ps.setInt(1, idPropietario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idInmueble");
                String direccion = rs.getString("direccion");
                double coste  = rs.getDouble("valor");
                Propietario propietario = PropietarioDAO.findById(idPropietario);
                Inmueble inmueble = new Inmueble(coste,direccion,id,propietario);
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmuebles;
    }

    /**
     * Actualiza el id del propetario del inmueble selecionado
     * @param idPropietario Se le pasa el id del propetario
     * @param id Se le pasa el id del inmueble
     */
    public static void updatePropietario(int idPropietario, int id){
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_PROPIETARIO)) {
            ps.setDouble(1, idPropietario);
            ps.setInt(2,id);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
