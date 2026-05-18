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
    private final static String SQL_FIND_BY_NAME_AND_PASSWORD = "SELECT * FROM Propietario WHERE nombre = ? AND contraseña = ?";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE Propietario SET contraseña = ? WHERE idPropietario = ?";
    private final static String SQL_UPDATE_DINERO = "UPDATE Propietario SET dinero = ? WHERE idPropietario = ?";
    private final static String SQL_DELETE = "DELETE FROM Propietario WHERE idPropietario = ?";

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

    public static Propietario findById(int id){
        Propietario propietario = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
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

    public static boolean updateDinero(double dinero, int id){
        boolean updated = false;
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_DINERO)) {
                ps.setDouble(1, dinero);
                ps.setInt(2,id);
                ps.executeUpdate();
                updated = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return updated;
    }

    public static boolean deletePropietarioById(int id) {
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
