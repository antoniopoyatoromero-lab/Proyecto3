package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FincaDAO {
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Finca Where idFinca = ?";
    private final static String SQL_INSERT = "INSERT INTO Finca (direccion,idTrabajador) VALUES (?,?)";
    private final static String SQL_UPDATE_TRABAJADOR = "UPDATE Finca SET idTrabajador = ? WHERE idFinca = ?";
    private final static String SQL_DELETE = "DELETE FROM Finca WHERE idFinca = ?";


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


    public static boolean addFinca(Finca finca) {
        boolean added = false;
        if (finca != null) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, finca.getDireccion());
                ps.setInt(2, finca.getTrabajador().getId());
                ps.executeUpdate();

                added = true;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean updateTrabajador(int idTrabajador, int id){
        boolean updated = false;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_TRABAJADOR)) {
            ps.setInt(1, idTrabajador);
            ps.setInt(2,id);
            ps.executeUpdate();
            updated = true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updated;
    }

    public static boolean deleteFincaById(int id) {
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
