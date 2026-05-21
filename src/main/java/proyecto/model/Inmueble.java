package proyecto.model;

public class Inmueble {
    private int id;
    private String direccion;
    private double coste;
    private Propietario propietario;


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

}
