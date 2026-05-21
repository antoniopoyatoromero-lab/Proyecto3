package proyecto.model;

public class ConceptoCuota {
    private int id;
    private String tipo;
    private Finca finca;

    public ConceptoCuota(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public ConceptoCuota(String tipo, Finca finca) {
        this.tipo = tipo;
        this.finca = finca;
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

}
