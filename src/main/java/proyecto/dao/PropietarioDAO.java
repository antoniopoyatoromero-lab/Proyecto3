package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropietarioDAO {
    private final static String SQL_ALL = "SELECT * FROM propietario";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Propietario Where idPropietario = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Propietario Where nombre = ?";
    private final static String SQL_FIND_BY_NAME_AND_PASSWORD = "SELECT * FROM Propietario WHERE nombre = ? AND contraseña = ?";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE Propietario SET contraseña = ? WHERE idPropietario = ?";
    private final static String SQL_UPDATE_DINERO = "UPDATE Propietario SET dinero = ? WHERE idPropietario = ?";

    /**
     * Busca todos los propietarios de la base de datos
     * @return Devuelve la lista de propietarios
     */
    public static List<Propietario> findAll() {
        List<Propietario> propietarios = new ArrayList<>();
        try (ResultSet rs = ConnectionDB.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("idpropietario");
                String password = rs.getString("contraseña");
                String nombre = rs.getString("nombre");
                Double dinero = rs.getDouble("dinero");
                Propietario propietario = new Propietario(id, password,nombre,dinero);
                propietarios.add(propietario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propietarios;
    }

    /**
     * Busca un propietario por el id
     * @param id Se le pasa el id del propietario
     * @return Devuelve el propietario encontrado
     */
    public static Propietario findById(int id){
        Propietario propietario = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String password = rs.getString("contraseña");
                String nombre = rs.getString("nombre");
                Double dinero = rs.getDouble("dinero");
                propietario = new Propietario(id, password,nombre,dinero);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propietario;
    }

    /**
     * Busca el propietario por el nombre
     * @param nombre Se le pasa el nombre del usuario
     * @return Devuelve el propietario encontrado
     */
    public static Propietario findByNombre(String nombre){
        Propietario propietario = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPropietario");
                String password = rs.getString("contraseña");
                Double dinero = rs.getDouble("dinero");
                propietario = new Propietario(id,password,nombre,dinero);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propietario;
    }

    /**
     * Se busca un propietario por el nombre y la contraseña
     * @param nombre Se le pasa el nombre del propietario
     * @param password Se le pasa la contraseña del propietario
     * @return Devuelve el propietario encontrado
     */
    public static Propietario findByNombreAndPassword(String nombre,String password) {
        Propietario propietario = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_NAME_AND_PASSWORD)) {
            ps.setString(1, nombre);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idPropietario");
                double dinero = rs.getDouble("dinero");
                propietario = new Propietario(id,password,nombre,dinero);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propietario;
    }

    /**
     * Se actualiza la contraseña del propietario
     * @param password Se le pasa la nueva contraseña
     * @param id Se le pasa el id del propietario
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

    /**
     * Se actualiza el dinero de un propietario
     * @param dinero Se le pasa el dinero a añadir
     * @param id Se le pasa el id del propietario
     */
    public static void updateDinero(double dinero, int id){
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_DINERO)) {
            ps.setDouble(1, dinero);
            ps.setInt(2,id);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
