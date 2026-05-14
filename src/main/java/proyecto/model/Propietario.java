package proyecto.model;

import java.util.List;

public class Propietario extends Persona {
    private double dinero;
    private List<Inmueble> inmuebles;

    public Propietario(int id, String password, String nombre, double dinero) {
        super(id, password, nombre);
        this.dinero = dinero;
    }

    public Propietario(String password, String nombre, double dinero) {
        super(password, nombre);
        this.dinero = dinero;
    }

    public Propietario(int id, String password, String nombre, double dinero, List<Inmueble> inmuebles) {
        super(id, password, nombre);
        this.dinero = dinero;
        this.inmuebles = inmuebles;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }

    @Override
    public String toString() {
        return super.toString()+ " dinero='"+dinero+"'}";
    }
}
