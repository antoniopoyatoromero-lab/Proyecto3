package proyecto.model;

public class Propietario extends Usuario {
    private double dinero;


    public Propietario(int id, String password, String nombre, double dinero) {
        super(id, password, nombre);
        this.dinero = dinero;
    }

    public Propietario(String password, String nombre, double dinero) {
        super(password, nombre);
        this.dinero = dinero;
    }


    public double getDinero() {
        return dinero;
    }





}
