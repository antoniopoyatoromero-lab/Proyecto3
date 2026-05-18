package proyecto.model;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String password;

    public Usuario(int id, String password, String nombre) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
    }

    public Usuario(String password, String nombre) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'';
    }
}
