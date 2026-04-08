package entidades;

public class Usuario {

    private String id;
    private String nombre;
    private Rol rol;

    public Usuario(String id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return id + "," + nombre + "," + rol.name();
    }
}
