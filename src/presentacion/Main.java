package presentacion;

import entidades.Acceso;
import entidades.Rol;
import entidades.Usuario;
import logicaNegocio.AccesoService;
import logicaNegocio.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UsuarioService usuarioService = new UsuarioService();
    private static final AccesoService  accesoService  = new AccesoService();
    private static final Scanner        scanner        = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> verUsuarios();
                case 3 -> eliminarUsuario();
                case 4 -> registrarEntrada();
                case 5 -> registrarSalida();
                case 6 -> verReportes();
                case 0 -> System.out.println("\nCerrando sistema...");
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // -------------------------------------------------------------------------
    // Menú principal
    // -------------------------------------------------------------------------

    private static void mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("   SISTEMA DE CONTROL DE ACCESO - LAB");
        System.out.println("==========================================");
        System.out.println(" 1. Registrar usuario");
        System.out.println(" 2. Ver usuarios");
        System.out.println(" 3. Eliminar usuario");
        System.out.println(" 4. Registrar entrada al laboratorio");
        System.out.println(" 5. Registrar salida del laboratorio");
        System.out.println(" 6. Ver reportes");
        System.out.println(" 0. Salir");
        System.out.println("==========================================");
        System.out.print("Opcion: ");
    }

    // -------------------------------------------------------------------------
    // Usuarios
    // -------------------------------------------------------------------------

    private static void registrarUsuario() {
        System.out.println("\n-- Registrar Usuario --");
        System.out.print("ID     : ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre : ");
        String nombre = scanner.nextLine().trim();

        Rol rol = leerRol();
        if (rol == null) return;

        try {
            usuarioService.registrar(id, nombre, rol);
            System.out.println("Usuario registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verUsuarios() {
        System.out.println("\n-- Lista de Usuarios --");
        try {
            List<Usuario> usuarios = usuarioService.listar();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
                return;
            }
            System.out.printf("%-10s %-25s %-12s%n", "ID", "NOMBRE", "ROL");
            System.out.println("-".repeat(50));
            for (Usuario u : usuarios) {
                System.out.printf("%-10s %-25s %-12s%n", u.getId(), u.getNombre(), u.getRol());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        System.out.println("\n-- Eliminar Usuario --");
        System.out.print("ID del usuario a eliminar: ");
        String id = scanner.nextLine().trim();
        try {
            usuarioService.eliminar(id);
            System.out.println("Usuario eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Accesos
    // -------------------------------------------------------------------------

    private static void registrarEntrada() {
        System.out.println("\n-- Registrar Entrada --");
        System.out.print("ID del usuario: ");
        String id = scanner.nextLine().trim();
        try {
            accesoService.registrarEntrada(id);
            System.out.println("Entrada registrada correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarSalida() {
        System.out.println("\n-- Registrar Salida --");
        System.out.print("ID del usuario: ");
        String id = scanner.nextLine().trim();
        try {
            accesoService.registrarSalida(id);
            System.out.println("Salida registrada correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Reportes
    // -------------------------------------------------------------------------

    private static void verReportes() {
        int opcion;
        do {
            System.out.println("\n-- Reportes --");
            System.out.println("1. Historial de accesos por usuario");
            System.out.println("2. Tiempo total en el laboratorio");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> reporteHistorial();
                case 2 -> reporteTiempoTotal();
                case 0 -> {}
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private static void reporteHistorial() {
        System.out.print("ID del usuario: ");
        String id = scanner.nextLine().trim();
        try {
            List<Acceso> historial = accesoService.historialPorUsuario(id);
            if (historial.isEmpty()) {
                System.out.println("No hay registros de acceso para este usuario.");
                return;
            }
            System.out.printf("%-30s %-30s%n", "ENTRADA", "SALIDA");
            System.out.println("-".repeat(62));
            for (Acceso a : historial) {
                String salida = a.getFechaHoraSalida() != null
                        ? a.getFechaHoraSalida().toString()
                        : "Aun dentro del laboratorio";
                System.out.printf("%-30s %-30s%n", a.getFechaHoraEntrada(), salida);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reporteTiempoTotal() {
        System.out.print("ID del usuario: ");
        String id = scanner.nextLine().trim();
        try {
            long minutos = accesoService.calcularTiempoTotalEnMinutos(id);
            long horas   = minutos / 60;
            long mins    = minutos % 60;
            System.out.printf("Tiempo total en el laboratorio: %d h %d min%n", horas, mins);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Utilidades
    // -------------------------------------------------------------------------

    private static Rol leerRol() {
        System.out.println("Rol:");
        System.out.println("  1. ESTUDIANTE");
        System.out.println("  2. DOCENTE");
        System.out.print("Opcion: ");
        int opcion = leerEntero();
        return switch (opcion) {
            case 1 -> Rol.ESTUDIANTE;
            case 2 -> Rol.DOCENTE;
            default -> {
                System.out.println("Rol no valido. Operacion cancelada.");
                yield null;
            }
        };
    }

    private static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
