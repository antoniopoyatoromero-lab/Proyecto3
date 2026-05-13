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

    public Inmueble getInmueble() {
        return inmueble;
    }

    public ConceptoCuota getConceptoCuota() {
        return conceptoCuota;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                ", conceptoCuota=" + conceptoCuota.getTipo() +
                ", inmueble=" + inmueble.getDireccion() +
                '}';
    }
}
