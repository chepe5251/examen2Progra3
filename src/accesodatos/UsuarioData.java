package accesodatos;

import entidades.Rol;
import entidades.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioData {

    private static final String ARCHIVO = "usuarios.txt";

    public void guardar(Usuario usuario) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(usuario.getId() + "," + usuario.getNombre() + "," + usuario.getRol().name());
            writer.newLine();
        }
    }

    public List<Usuario> listar() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Usuario u : lista) {
                if (!u.getId().equals(id)) {
                    writer.write(u.getId() + "," + u.getNombre() + "," + u.getRol().name());
                    writer.newLine();
                }
            }
        }
    }

    private Usuario parsear(String linea) {
        String[] partes = linea.split(",");
        return new Usuario(
            partes[0].trim(),
            partes[1].trim(),
            Rol.valueOf(partes[2].trim())
        );
    }
}
