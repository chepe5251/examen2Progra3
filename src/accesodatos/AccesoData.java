package accesodatos;

import entidades.Acceso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccesoData {

    private static final Path ARCHIVO = RutaDatos.resolver("accesos.txt");

    public void guardar(Acceso acceso) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                ARCHIVO,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(serializar(acceso));
            writer.newLine();
        }
    }

    public List<Acceso> listar() throws IOException {
        List<Acceso> lista = new ArrayList<>();
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

    public void eliminar(String idUsuario) throws IOException {
        List<Acceso> lista = listar();
        try (BufferedWriter writer = Files.newBufferedWriter(
                ARCHIVO,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (Acceso a : lista) {
                if (!a.getIdUsuario().equals(idUsuario)) {
                    writer.write(serializar(a));
                    writer.newLine();
                }
            }
        }
    }

    public void actualizarSalida(String idUsuario, LocalDateTime fechaSalida) throws IOException {
        List<Acceso> lista = listar();
        try (BufferedWriter writer = Files.newBufferedWriter(
                ARCHIVO,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (Acceso a : lista) {
                if (a.getIdUsuario().equals(idUsuario) && a.getFechaHoraSalida() == null) {
                    a.setFechaHoraSalida(fechaSalida);
                }
                writer.write(serializar(a));
                writer.newLine();
            }
        }
    }

    private Acceso parsear(String linea) throws IOException {
        String[] partes = linea.split(",", 3);
        if (partes.length != 3) {
            throw new IOException("Formato invalido en " + ARCHIVO.getFileName() + ": " + linea);
        }

        try {
            String idUsuario = partes[0].trim();
            LocalDateTime entrada = LocalDateTime.parse(partes[1].trim());
            LocalDateTime salida = partes[2].trim().equals("null") ? null : LocalDateTime.parse(partes[2].trim());
            return new Acceso(idUsuario, entrada, salida);
        } catch (RuntimeException e) {
            throw new IOException("Formato invalido en " + ARCHIVO.getFileName() + ": " + linea, e);
        }
    }

    private String serializar(Acceso acceso) {
        String salida = acceso.getFechaHoraSalida() != null
                ? acceso.getFechaHoraSalida().toString()
                : "null";
        return acceso.getIdUsuario() + "," + acceso.getFechaHoraEntrada() + "," + salida;
    }
}