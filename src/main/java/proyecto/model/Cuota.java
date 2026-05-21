package proyecto.model;

public class Cuota {
    private int id;
    private String nombre;
    private String descripcion;
    private double costo;
    private ConceptoCuota conceptoCuota;
    private Inmueble inmueble;

    public Cuota(int id, String nombre, String descripcion, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public Cuota(String nombre, String descripcion, double costo,ConceptoCuota conceptoCuota,Inmueble inmueble) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.conceptoCuota = conceptoCuota;
        this.inmueble = inmueble;
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

    public double getCosto() {
        return costo;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public ConceptoCuota getConceptoCuota() {
        return conceptoCuota;
    }

}
