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

    public static Cuota findById(int id){
        Cuota cuota = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                String descripcion = rs.getString("descripcion");
                String nombre = rs.getString("nombre");
                int costo = rs.getInt("costo");
               cuota = new Cuota(id,nombre,descripcion,costo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuota;
    }

    public static List<Cuota> findByIdInmueble(int idInmueble){
        ArrayList<Cuota> cuotas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_INMUEBLE)) {
            ps.setInt(1, idInmueble);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                int id = rs.getInt("idcuota");
                String descripcion = rs.getString("descripcion");
                String nombre = rs.getString("nombre");
                int costo = rs.getInt("costo");
                Cuota cuota = new Cuota(id,nombre,descripcion,costo);
                cuotas.add(cuota);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuotas;
    }

    public static boolean addCuota(Cuota cuota) {
        boolean added = false;
        if (cuota != null) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, cuota.getNombre());
                ps.setString(2, cuota.getDescripcion());
                ps.setInt(3, cuota.getCosto());
                ps.setInt(4,cuota.getConceptoCuota().getId());
                ps.setInt(5,cuota.getInmueble().getId());
                ps.executeUpdate();

                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean deleteCuotaById(int id) {
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
