package accesodatos;

import entidades.Acceso;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccesoData {

    private static final String ARCHIVO = "accesos.txt";

    public void guardar(Acceso acceso) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String salida = acceso.getFechaHoraSalida() != null
                    ? acceso.getFechaHoraSalida().toString()
                    : "null";
            writer.write(acceso.getIdUsuario() + "," + acceso.getFechaHoraEntrada() + "," + salida);
            writer.newLine();
        }
    }

    public List<Acceso> listar() throws IOException {
        List<Acceso> lista = new ArrayList<>();
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

    public void eliminar(String idUsuario) throws IOException {
        List<Acceso> lista = listar();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Acceso a : lista) {
                if (!a.getIdUsuario().equals(idUsuario)) {
                    String salida = a.getFechaHoraSalida() != null
                            ? a.getFechaHoraSalida().toString()
                            : "null";
                    writer.write(a.getIdUsuario() + "," + a.getFechaHoraEntrada() + "," + salida);
                    writer.newLine();
                }
            }
        }
    }

    public void actualizarSalida(String idUsuario, LocalDateTime fechaSalida) throws IOException {
        List<Acceso> lista = listar();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Acceso a : lista) {
                if (a.getIdUsuario().equals(idUsuario) && a.getFechaHoraSalida() == null) {
                    a.setFechaHoraSalida(fechaSalida);
                }
                String salida = a.getFechaHoraSalida() != null
                        ? a.getFechaHoraSalida().toString()
                        : "null";
                writer.write(a.getIdUsuario() + "," + a.getFechaHoraEntrada() + "," + salida);
                writer.newLine();
            }
        }
    }

    private Acceso parsear(String linea) {
        String[] partes = linea.split(",");
        String idUsuario      = partes[0].trim();
        LocalDateTime entrada = LocalDateTime.parse(partes[1].trim());
        LocalDateTime salida  = partes[2].trim().equals("null") ? null : LocalDateTime.parse(partes[2].trim());
        return new Acceso(idUsuario, entrada, salida);
    }
}
