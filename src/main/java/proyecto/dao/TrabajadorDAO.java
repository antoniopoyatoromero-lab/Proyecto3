package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Propietario;
import proyecto.model.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrabajadorDAO {
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Trabajador Where idTrabajador = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Trabajador Where nombre = ?";
    private final static String SQL_FIND_BY_NAME_AND_PASSWORD = "SELECT * FROM Trabajador WHERE nombre = ? AND contraseña = ?";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE Trabajador SET contraseña = ? WHERE idTrabajador = ?";

    /**
     * Se busca un trabajador por un id
     * @param id Se le pasa el id del trabajador
     * @return Devuelve el trabajador encontrado
     */
    public static Trabajador findById(int id){
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

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

    /**
     * Se busca un trabajador por el nombre y por la contraseña
     * @param nombre Se le pasa el nombre del trabajador
     * @param password Se le pasa la contraseña del trabajador
     * @return Devuelve el trabajador
     */
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

    /**
     * Busca un trabajador por el nombre
     * @param nombre Se le pasa el nombre del trabajador
     * @return Devuelve el trabajador escogido
     */
    public static Trabajador findByNombre(String nombre){
        Trabajador trabajador = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idTrabajador");
                String password = rs.getString("contraseña");
                String dni = rs.getString("dni");
                trabajador = new Trabajador(id,password,nombre,dni);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajador;
    }

    /**
     * Se actualiza la contraseña del trabajador
     * @param password Se le pasa la nueva contraseña
     * @param id Se le pasa el id del trabajador
     */
    public static void updatePassword(String password, int id){
        if(!password.isEmpty()){
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_PASSWORD)) {
                ps.setString(1, password);
                ps.setInt(2,id);
                ps.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
