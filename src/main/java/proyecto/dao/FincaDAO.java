package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FincaDAO {

    private final static String SQL_FIND_BY_ID_TRABAJADOR = "SELECT * FROM Finca Where idTrabajador = ?";

    /**
     * Se busca todos las fincas relacionadas por un trabajador
     * @param idTrabajador Se le pasa el id del trabajador
     * @return Devuelve la lista de fincas que estan relacionadas con el trabajador
     */
    public static List<Finca> findByIdTrabajador(int idTrabajador){
        ArrayList<Finca> fincas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_TRABAJADOR)) {
            ps.setInt(1, idTrabajador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idFinca");
                String direccion = rs.getString("direccion");
                Trabajador trabajador = TrabajadorDAO.findById(idTrabajador);
                Finca finca = new Finca(direccion,id,trabajador);
                fincas.add(finca);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fincas;
    }
}
