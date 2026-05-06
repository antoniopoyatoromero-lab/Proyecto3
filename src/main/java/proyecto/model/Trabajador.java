package proyecto.model;

public class Trabajador extends Persona {
    private String dni;

    public Trabajador(int id, String password, String nombre, String dni) {
        super(id, password, nombre);
        this.dni = dni;
    }
}
