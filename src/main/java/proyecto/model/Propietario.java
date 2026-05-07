package proyecto.model;

import java.util.List;

public class Propietario extends Persona {
    private double dinero;
    private List<Inmueble> inmuebles;

    public Propietario(int id, String password, String nombre, double dinero) {
        super(id, password, nombre);
        this.dinero = dinero;
    }

    public double getDinero() {
        return dinero;
    }

    @Override
    public String toString() {
        return "id='" + id + "'" +
                ", nombre='" + nombre + "'" +
                ", password='" + password + "'" +
                ", dinero='" + dinero + "'" +
                ", inmuebles='" + inmuebles + "'";
    }
}
