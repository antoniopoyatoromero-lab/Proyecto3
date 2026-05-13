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
        return super.toString() + " sdni='"+dni+"' fincas='"+fincas.stream().map(Finca::getDireccion).toList()+"'}";
    }
}
