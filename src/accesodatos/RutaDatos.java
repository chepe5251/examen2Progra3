package accesodatos;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

final class RutaDatos {

    private RutaDatos() {
    }

    static Path resolver(String nombreArchivo) {
        try {
            Path base = Paths.get(RutaDatos.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (!base.toFile().isDirectory()) {
                base = base.getParent();
            }
            return base.resolve(nombreArchivo).normalize();
        } catch (URISyntaxException | IllegalArgumentException e) {
            return Paths.get(nombreArchivo).toAbsolutePath().normalize();
        }
    }
}