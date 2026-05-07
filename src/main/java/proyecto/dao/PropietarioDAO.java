package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Propietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropietarioDAO {
    private final static String SQL_ALL = "SELECT * FROM propietario";

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
}
