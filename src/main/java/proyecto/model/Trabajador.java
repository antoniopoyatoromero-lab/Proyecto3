package proyecto.model;

import java.util.List;

public class Trabajador extends Persona {
    private String dni;
    private List <Finca> fincas;

    public Trabajador(int id, String password, String nombre, String dni) {
        super(id, password, nombre);
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", dni='" + dni + '\'' +
                ", fincas=" + fincas;
    }
}
