package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Cuota;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuotaDAO {

    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cuota Where idCuota = ?";
    private final static String SQL_FIND_BY_ID_INMUEBLE = "SELECT * FROM Cuota where idInmueble =?";
    private final static String SQL_INSERT = "INSERT INTO Cuota (nombre,descripcion,costo,idConceptoCuota,idInmueble) VALUES (?,?,?,?,?)";
    private final static String SQL_DELETE = "DELETE FROM Cuota WHERE idCuota = ?";

    /**
     * Se busca la cuota en la base de datos por el id
     * @param id Se pasa el id de la cuota
     * @return Devuelve la cuota encontrada
     */
    public static Cuota findById(int id){
        Cuota cuota = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String descripcion = rs.getString("descripcion");
                String nombre = rs.getString("nombre");
                double costo = rs.getDouble("costo");
               cuota = new Cuota(id,nombre,descripcion,costo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuota;
    }

    /**
     * Busca en la base de datos todas las coutas por la id de un inmueble
     * @param idInmueble Se le pasa el id del inmueble
     * @return Devuelve una lista de las cuotas que contiene el id del inmueble
     */
    public static List<Cuota> findByIdInmueble(int idInmueble){
        ArrayList<Cuota> cuotas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_INMUEBLE)) {
            ps.setInt(1, idInmueble);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idcuota");
                String descripcion = rs.getString("descripcion");
                String nombre = rs.getString("nombre");
                double costo = rs.getDouble("costo");
                Cuota cuota = new Cuota(id,nombre,descripcion,costo);
                cuotas.add(cuota);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuotas;
    }

    /**
     * Añade la cuota creada a la base de datos
     * @param cuota Se le pasa la cuota que se crea
     */
    public static void addCuota(Cuota cuota) {
        if (cuota != null) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, cuota.getNombre());
                ps.setString(2, cuota.getDescripcion());
                ps.setDouble(3, cuota.getCosto());
                ps.setInt(4,cuota.getConceptoCuota().getId());
                ps.setInt(5,cuota.getInmueble().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Se borra la cuota seleccionada
     * @param id Se le pasa el id de la cuota
     */
    public static void deleteCuotaById(int id) {
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
