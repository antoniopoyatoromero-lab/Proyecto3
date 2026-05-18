package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FincaDAO {
    private final static String SQL_ALL = "SELECT * FROM finca";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Finca Where idFinca = ?";

    public static List<Finca> findAll() {
        List<Finca> fincas = new ArrayList<>();
        try (ResultSet rs = ConnectionDB.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("idFinca");
                String direccion = rs.getString("direccion");
                Finca finca = new Finca(direccion,id);
                fincas.add(finca);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fincas;
    }

    public static Finca findById(int id){
        Finca finca = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                String direccion = rs.getString("direccion");
                finca = new Finca(direccion,id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finca;
    }
}
