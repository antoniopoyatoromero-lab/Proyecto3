package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Propietario;
import proyecto.model.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrabajadorDAO {
    private final static String SQL_ALL = "SELECT * FROM inmuebles";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Trabajador Where idTrabajador = ?";
    private final static String SQL_FIND_BY_PASSWORD = "SELECT * FROM Trabajador where contraseña =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Trabajador where nombre =?";
    private final static String SQL_INSERT = "INSERT INTO Trabajador (nombre,contraseña,dni) VALUES (?,?,?)";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE Trabajador SET contraseña = ? WHERE idTrabajador = ?";
    private final static String SQL_DELETE = "DELETE FROM Trabajador WHERE idTrabajador = ?";
    private final static String SQL_FIND_BY_ID_PROPIETARIO = "SELECT * FROM Inmueble where idPropietario =?";
    private final static String SQL_UPDATE_TRABAJADOR = "UPDATE Inmueble SET costo = ? WHERE idInmueble = ?";

    public static Trabajador findById(int id){
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                String password = rs.getString("contraseña");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                trabajador = new Trabajador(id, password,nombre,dni);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajador;
    }

    private static Trabajador findByPassword(String password) {
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_PASSWORD)) {
            ps.setString(1, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idTrabajador");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                trabajador = new Trabajador(id,password,nombre,dni);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajador;
    }
    private static Trabajador findByNombre(String nombre) {
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idPropietario");
                String password = rs.getString("contraseña");
                String dni = rs.getString("dni");
                trabajador = new Trabajador(id,password,nombre,dni);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajador;
    }

    public static boolean addTrabajador(Trabajador trabajador) {
        boolean added = false;
        if ((trabajador != null)&&(findByPassword(trabajador.getPassword())==null)) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, trabajador.getNombre());
                ps.setString(2, trabajador.getPassword());
                ps.setString(3, trabajador.getDni());
                ps.executeUpdate();

                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean updatePassword(String password, int id){
        boolean updated = false;
        if(password.isEmpty()){
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_PASSWORD)) {
                ps.setString(1, password);
                ps.setInt(2,id);
                ps.executeUpdate();
                updated = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    public static boolean deleteTrabajadorById(int id) {
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
