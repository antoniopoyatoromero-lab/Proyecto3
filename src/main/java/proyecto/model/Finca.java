package proyecto.model;

import java.util.List;

public class Finca {
    private int id;
    private String direccion;
    private Trabajador trabajador;

    public Finca(String direccion, int id) {
        this.direccion = direccion;
        this.id = id;
    }

    public Finca(String direccion, int id, Trabajador trabajador) {
        this.direccion = direccion;
        this.id = id;
        this.trabajador = trabajador;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }
}
