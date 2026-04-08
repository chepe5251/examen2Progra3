package logicaNegocio;

import accesodatos.UsuarioData;
import entidades.Rol;
import entidades.Usuario;

import java.io.IOException;
import java.util.List;

public class UsuarioService {

    private final UsuarioData usuarioData;

    public UsuarioService() {
        this.usuarioData = new UsuarioData();
    }

    public void registrar(String id, String nombre, Rol rol) throws IOException {
        validarCamposUsuario(id, nombre, rol);

        if (existeId(id)) {
            throw new IllegalArgumentException("Ya existe un usuario con el ID: " + id);
        }

        usuarioData.guardar(new Usuario(id, nombre, rol));
    }

    public List<Usuario> listar() throws IOException {
        return usuarioData.listar();
    }

    public void eliminar(String id) throws IOException {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        if (!existeId(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        usuarioData.eliminar(id);
    }

    public boolean existeId(String id) throws IOException {
        return usuarioData.listar().stream()
                .anyMatch(u -> u.getId().equals(id));
    }

    private void validarCamposUsuario(String id, String nombre, Rol rol) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }
    }
}
