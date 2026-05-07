package proyecto.model;

public class Cuota {
    private int id;
    private String nombre;
    private String descripcion;
    private int costo;
    private ConceptoCuota conceptoCuota;
    private Inmueble inmueble;

    public Cuota(int id, String nombre, String descripcion, int costo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCosto() {
        return costo;
    }
}
