package proyecto.model;

public class Propietario extends Persona {
    private int dinero;

    public Propietario(int id, String password, String nombre, int dinero) {
        super(id, password, nombre);
        this.dinero = dinero;
    }
}
