package proyecto.model;

import java.util.List;

public class Finca {
    private int id;
    private String direccion;
    private Trabajador trabajador;
    private List<ConceptoCuota> conceptosCuotas;
    private List<Inmueble> inmuebles;

    public Finca(String direccion, int id) {
        this.direccion = direccion;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Finca{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", trabajador=" + trabajador.nombre +
                ", conceptosCuotas=" + conceptosCuotas.stream().map(ConceptoCuota :: getTipo).toList() +
                ", inmuebles=" + inmuebles.stream().map(Inmueble :: getDireccion).toList() +
                '}';
    }
}
