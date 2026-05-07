package proyecto.model;

import java.util.List;

public class Inmueble {
    private int id;
    private String direccion;
    private double coste;
    private Propietario propietario;
    private List<Cuota> cuotas;

    public Inmueble(double coste, String direccion, int id) {
        this.coste = coste;
        this.direccion = direccion;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getCoste() {
        return coste;
    }
}
