package proyecto.dao;

import proyecto.dataAccess.ConnectionDB;
import proyecto.model.Cuota;
import proyecto.model.Finca;
import proyecto.model.Inmueble;
import proyecto.model.Propietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InmuebleDAO {
    private final static String SQL_ALL = "SELECT * FROM inmuebles";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Propietario Where idPropietario = ?";
    private final static String SQL_FIND_BY_ID_PROPIETARIO = "SELECT * FROM Inmueble where idPropietario =?";
    private final static String SQL_INSERT = "INSERT INTO Inmueble (direccion,valor,idFinca,idPropietario) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE_COSTO = "UPDATE Inmueble SET costo = ? WHERE idInmueble = ?";
    private final static String SQL_UPDATE_PROPIETARIO = "UPDATE Inmueble SET idPropietario = ? WHERE idInmueble = ?";


    public static List<Inmueble> findAll() {
        List<Inmueble> inmuebles = new ArrayList<>();
        try (ResultSet rs = ConnectionDB.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("idInmueble");
                String direccion = rs.getString("direccion");
                Double coste = rs.getDouble("valor");
                Inmueble inmueble = new Inmueble(coste,direccion,id);
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmuebles;
    }

    public static List<Inmueble> findAllEager() {
        List<Inmueble> inmuebles = new ArrayList<>();
        try (ResultSet rs = ConnectionDB.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("idInmueble");
                String direccion = rs.getString("direccion");
                Double coste = rs.getDouble("valor");
                Propietario propietario = PropietarioDAO.findById(rs.getInt("idPropietario"));
                List <Cuota> cuotas = CuotaDAO.findByIdInmueble(id);
                Finca finca = FincaDAO.findById(rs.getInt("idFinca"));
                Inmueble inmueble = new Inmueble(coste,direccion,id,propietario,cuotas,finca);
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmuebles;
    }

    public static Inmueble findById(int id){
        Inmueble inmueble = null;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                String direccion = rs.getString("direccion");
                Double coste = rs.getDouble("valor");
                inmueble = new Inmueble(coste,direccion,id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmueble;
    }

    public static List<Inmueble> findByIdPropietario(int idPropietario){
        ArrayList<Inmueble> inmuebles = new ArrayList<>();
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_FIND_BY_ID_PROPIETARIO)) {
            ps.setInt(1, idPropietario);
            ResultSet rs = ps.executeQuery();

            //4. recorrer el resultado, next() devuelve true si hay registro, false si no.
            while (rs.next()) {
                int id = rs.getInt("idInmueble");
                String direccion = rs.getString("direccion");
                double coste  = rs.getDouble("valor");
                Propietario propietario = PropietarioDAO.findById(rs.getInt("idPropietario"));
                Inmueble inmueble = new Inmueble(coste,direccion,id,propietario);
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmuebles;
    }

    public static boolean addInmueble(Inmueble inmueble) {
        boolean added = false;
        if (inmueble != null) {
            try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, inmueble.getDireccion());
                ps.setDouble(2, inmueble.getCoste());
                ps.setInt(3, inmueble.getFinca().getId());
                ps.setInt(4,inmueble.getPropietario().getId());
                ps.executeUpdate();

                added = true;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean updateCosto(double costo, int id){
        boolean updated = false;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_COSTO)) {
            ps.setDouble(1, costo);
            ps.setInt(2,id);
            ps.executeUpdate();
            updated = true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updated;
    }

    public static boolean updatePropietario(int idPropietario, int id){
        boolean updated = false;
        try (PreparedStatement ps = ConnectionDB.getConnection().prepareStatement(SQL_UPDATE_COSTO)) {
            ps.setDouble(1, idPropietario);
            ps.setInt(2,id);
            ps.executeUpdate();
            updated = true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updated;
    }
}
