package logicaNegocio;

import accesodatos.AccesoData;
import accesodatos.UsuarioData;
import entidades.Rol;
import entidades.Usuario;

import java.io.IOException;
import java.util.List;

public class UsuarioService {

    private final AccesoData accesoData;
    private final UsuarioData usuarioData;

    public UsuarioService() {
        this.accesoData = new AccesoData();
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
        validarId(id);
        if (!existeId(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        if (tieneAccesoActivo(id)) {
            throw new IllegalStateException(
                "No se puede eliminar el usuario " + id + " porque tiene una entrada activa registrada."
            );
        }
        usuarioData.eliminar(id);
    }

    public boolean existeId(String id) throws IOException {
        return usuarioData.listar().stream()
                .anyMatch(u -> u.getId().equals(id));
    }

    private void validarCamposUsuario(String id, String nombre, Rol rol) {
        validarId(id);
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        validarSinComas(nombre, "El nombre");
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }
    }

    private void validarId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacio.");
        }
        validarSinComas(id, "El ID");
    }

    private void validarSinComas(String valor, String campo) {
        if (valor.contains(",")) {
            throw new IllegalArgumentException(campo + " no puede contener comas.");
        }
    }

    private boolean tieneAccesoActivo(String id) throws IOException {
        return accesoData.listar().stream()
                .anyMatch(a -> a.getIdUsuario().equals(id) && a.getFechaHoraSalida() == null);
    }
}