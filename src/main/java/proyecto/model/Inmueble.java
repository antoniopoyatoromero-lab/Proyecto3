package proyecto.model;

import java.util.List;

public class Inmueble {
    private int id;
    private String direccion;
    private double coste;
    private Propietario propietario;
    private Finca finca;
    private List<Cuota> cuotas;

    public Inmueble(double coste, String direccion, int id, Propietario propietario, List<Cuota> cuotas, Finca finca) {
        this.coste = coste;
        this.direccion = direccion;
        this.id = id;
        this.propietario = propietario;
        this.cuotas = cuotas;
        this.finca = finca;
    }

    public Inmueble(double coste, String direccion, int id, Propietario propietario) {
        this.coste = coste;
        this.direccion = direccion;
        this.id = id;
        this.propietario = propietario;
    }

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

    public Propietario getPropietario() {
        return propietario;
    }

    public Finca getFinca() {
        return finca;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", coste=" + coste +
                ", propietario=" + propietario.getNombre() +
                ", cuotas=" + cuotas.stream().map(Cuota::getNombre).toList() +
                '}';
    }
}
