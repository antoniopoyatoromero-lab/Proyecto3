package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Propietario;
import proyecto.model.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrabajadorDAO {
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Trabajador Where idTrabajador = ?";
    private final static String SQL_FIND_BY_PASSWORD = "SELECT * FROM Trabajador where contraseña =?";
    private final static String SQL_FIND_BY_NAME_AND_PASSWORD = "SELECT * FROM Trabajador WHERE nombre = ? AND contraseña = ?";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE Trabajador SET contraseña = ? WHERE idTrabajador = ?";


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

    public static Trabajador findByPassword(String password) {
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
    public static Trabajador findByNombreAndPassword(String nombre,String password) {
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_NAME_AND_PASSWORD)) {
            ps.setString(1, nombre);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idTrabajador");
                String dni = rs.getString("dni");
                trabajador = new Trabajador(id,password,nombre,dni);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajador;
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

}
