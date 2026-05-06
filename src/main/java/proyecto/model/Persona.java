package proyecto.model;

public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String password;

    public Persona(int id, String password, String nombre) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
