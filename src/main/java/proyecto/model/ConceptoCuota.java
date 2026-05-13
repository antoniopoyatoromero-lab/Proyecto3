package proyecto.model;

import java.util.List;

public class ConceptoCuota {
    private int id;
    private String tipo;
    private Finca finca;
    private List<Cuota> cuotas;

    public ConceptoCuota(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Finca getFinca() {
        return finca;
    }

    @Override
    public String toString() {
        return "ConceptoCuota{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", finca=" + finca.getDireccion() +
                ", cuotas=" + cuotas.stream().map(Cuota :: getNombre).toList() +
                '}';
    }
}
