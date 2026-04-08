package accesodatos;

import entidades.Rol;
import entidades.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UsuarioData {

    private static final Path ARCHIVO = RutaDatos.resolver("usuarios.txt");

    public void guardar(Usuario usuario) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                ARCHIVO,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(serializar(usuario));
            writer.newLine();
        }
    }

    public List<Usuario> listar() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        if (Files.notExists(ARCHIVO)) {
            return lista;
        }

        try (BufferedReader reader = Files.newBufferedReader(ARCHIVO, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.isBlank()) {
                    lista.add(parsear(linea));
                }
            }
        }
        return lista;
    }

    public void eliminar(String id) throws IOException {
        List<Usuario> lista = listar();
        try (BufferedWriter writer = Files.newBufferedWriter(
                ARCHIVO,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (Usuario u : lista) {
                if (!u.getId().equals(id)) {
                    writer.write(serializar(u));
                    writer.newLine();
                }
            }
        }
    }

    private Usuario parsear(String linea) throws IOException {
        String[] partes = linea.split(",", 3);
        if (partes.length != 3) {
            throw new IOException("Formato invalido en " + ARCHIVO.getFileName() + ": " + linea);
        }

        try {
            return new Usuario(
                partes[0].trim(),
                partes[1].trim(),
                Rol.valueOf(partes[2].trim())
            );
        } catch (IllegalArgumentException e) {
            throw new IOException("No se pudo leer el rol en " + ARCHIVO.getFileName() + ": " + linea, e);
        }
    }

    private String serializar(Usuario usuario) {
        return usuario.getId() + "," + usuario.getNombre() + "," + usuario.getRol().name();
    }
}