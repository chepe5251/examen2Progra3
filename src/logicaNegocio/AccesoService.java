package logicaNegocio;

import accesodatos.AccesoData;
import accesodatos.UsuarioData;
import entidades.Acceso;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AccesoService {

    private final AccesoData accesoData;
    private final UsuarioData usuarioData;

    public AccesoService() {
        this.accesoData  = new AccesoData();
        this.usuarioData = new UsuarioData();
    }

    public void registrarEntrada(String idUsuario) throws IOException {
        validarIdNoVacio(idUsuario);
        validarUsuarioExiste(idUsuario);
        validarSinEntradaActiva(idUsuario);
        accesoData.guardar(new Acceso(idUsuario, LocalDateTime.now()));
    }

    private void validarSinEntradaActiva(String idUsuario) throws IOException {
        if (tieneEntradaActiva(idUsuario)) {
            throw new IllegalStateException(
                "Doble entrada no permitida: el usuario '" + idUsuario +
                "' ya tiene una entrada activa sin salida registrada."
            );
        }
    }

    public void registrarSalida(String idUsuario) throws IOException {
        validarIdNoVacio(idUsuario);
        validarUsuarioExiste(idUsuario);

        if (!tieneEntradaActiva(idUsuario)) {
            throw new IllegalStateException(
                "El usuario " + idUsuario + " no tiene una entrada activa registrada."
            );
        }

        accesoData.actualizarSalida(idUsuario, LocalDateTime.now());
    }

    public List<Acceso> historialPorUsuario(String idUsuario) throws IOException {
        validarIdNoVacio(idUsuario);
        validarUsuarioExiste(idUsuario);

        return accesoData.listar().stream()
                .filter(a -> a.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    public long calcularTiempoTotalEnMinutos(String idUsuario) throws IOException {
        List<Acceso> historial = historialPorUsuario(idUsuario);

        return historial.stream()
                .filter(a -> a.getFechaHoraSalida() != null)
                .mapToLong(a -> Duration.between(a.getFechaHoraEntrada(), a.getFechaHoraSalida()).toMinutes())
                .sum();
    }

    private boolean tieneEntradaActiva(String idUsuario) throws IOException {
        return accesoData.listar().stream()
                .anyMatch(a -> a.getIdUsuario().equals(idUsuario) && a.getFechaHoraSalida() == null);
    }

    private void validarIdNoVacio(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío.");
        }
    }

    private void validarUsuarioExiste(String idUsuario) throws IOException {
        boolean existe = usuarioData.listar().stream()
                .anyMatch(u -> u.getId().equals(idUsuario));
        if (!existe) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + idUsuario);
        }
    }
}
