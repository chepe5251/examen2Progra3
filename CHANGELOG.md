# Registro de Cambios — CHANGELOG

**Proyecto:** Sistema de Control de Acceso a Laboratorio  
**Lenguaje:** Java  
**Formato:** [Versionado Semántico](https://semver.org/lang/es/)

Todos los cambios relevantes de cada versión se documentan aquí, ordenados de la más reciente a la más antigua.

---

## [v2.0] — 2026-04-07 — Interfaz gráfica con JavaFX

### Descripción
Se migró completamente la capa `Presentacion` de un menú de consola a una interfaz gráfica
de escritorio desarrollada con JavaFX. Se mantiene intacta la arquitectura por capas: la
interfaz solo se comunica con `LogicaNegocio`, sin acceso directo a `AccesoDatos`.

### Añadido
- `MainApp.java` — punto de entrada de la aplicación JavaFX, configura el `Stage` principal
- `MainController.java` — ventana raíz con sidebar de navegación lateral y área de contenido dinámica
- `DashboardController.java` — panel de inicio con tarjetas de métricas (usuarios registrados,
  usuarios dentro del laboratorio, total de accesos) e información del sistema
- `UsuariosController.java` — módulo de gestión de usuarios con formulario de registro y tabla
  interactiva con badges de rol y botón de eliminación por fila
- `AccesosController.java` — módulo de entrada/salida con tarjetas visuales independientes,
  retroalimentación de estado en línea y notas de política de acceso
- `ReportesController.java` — módulo de historial con búsqueda por usuario, tabla de accesos
  con columna de duración calculada y tarjetas de resumen (total registros / tiempo total)
- `EstilosUI.java` — utilidad centralizada de estilos: paleta de colores, métodos de estilo
  para botones, campos, textos y alertas visuales estandarizadas

### Modificado
- `AccesoService.java` — se añadieron dos métodos para proveer métricas al dashboard:
  - `listarTodosLosAccesos()` — retorna todos los registros de acceso
  - `contarDentroDelLaboratorio()` — cuenta usuarios con entrada activa sin salida

### Mantenido
- `Main.java` (consola) se conserva como modo alternativo de ejecución sin JavaFX
- Toda la arquitectura por capas permanece intacta
- La persistencia en archivos `.txt` no fue modificada
- Todas las validaciones de `LogicaNegocio` siguen siendo la única fuente de lógica

### Paleta de colores de la interfaz
| Color | Uso |
|-------|-----|
| `#0f2744` | Sidebar de navegación |
| `#3b82f6` | Botón activo, acciones primarias |
| `#10b981` | Éxito, entrada, badge "Activo" |
| `#ef4444` | Error, eliminación |
| `#f59e0b` | Advertencias, métrica de accesos |
| `#f1f5f9` | Fondo general de la aplicación |

---

## [v1.3] — 2026-04-07 — Validaciones y pruebas

### Descripción
Se completó la cobertura de validaciones críticas del sistema. Se aisló la regla
de doble entrada en un método privado explícito dentro de `AccesoService`, mejorando
la legibilidad y el mantenimiento de la lógica de control de acceso.

### Corregido
- Se extrajo la validación de doble entrada al método privado `validarSinEntradaActiva()`
  en `AccesoService`, separando la regla de negocio de la orquestación del flujo principal
- Se mejoró el mensaje de error para el caso de doble entrada, haciéndolo más
  descriptivo e identificable en tiempo de ejecución

### Validaciones activas en esta versión
- Bloqueo de doble entrada sin salida previa registrada
- Bloqueo de salida sin entrada activa
- Rechazo de ID de usuario vacío o nulo en todos los métodos de servicio
- Rechazo de nombre vacío o nulo al registrar usuario
- Rechazo de rol nulo al registrar usuario
- Verificación de existencia del usuario antes de registrar cualquier acceso
- El cálculo de tiempo total excluye registros sin salida registrada

---

## [v1.2] — 2026-04-07 — Lógica de negocio e interfaz de consola

### Descripción
Se implementaron las capas `LogicaNegocio` y `Presentacion` (consola), completando la
arquitectura funcional del sistema. Toda la lógica de validación quedó centralizada en los
servicios, y la interacción con el usuario se delegó exclusivamente a `Main.java`.

### Añadido
- `UsuarioService` con métodos `registrar()`, `listar()`, `eliminar()` y `existeId()`
- `AccesoService` con métodos `registrarEntrada()`, `registrarSalida()`,
  `historialPorUsuario()` y `calcularTiempoTotalEnMinutos()`
- Método privado `tieneEntradaActiva()` para consultar el estado actual del usuario
- Método privado `validarUsuarioExiste()` reutilizable en `AccesoService`
- `Main.java` con menú principal interactivo en consola usando `Scanner`
- Submenú de reportes con historial de accesos y tiempo total en laboratorio
- Método auxiliar `leerEntero()` para manejo seguro de entradas numéricas inválidas
- Método `leerRol()` para selección tipada del enum `Rol`

### Restricciones aplicadas
- La capa `Presentacion` no importa ni referencia ninguna clase de `AccesoDatos`
- Todas las excepciones de negocio son capturadas en la presentación y mostradas al usuario

---

## [v1.1] — 2026-04-07 — Implementación de acceso a datos

### Descripción
Se implementó la capa `AccesoDatos` con persistencia completa en archivos `.txt`.
Las clases de esta capa se limitan exclusivamente a operaciones de lectura y escritura;
no contienen lógica de negocio ni validaciones de dominio.

### Añadido
- `UsuarioData` con métodos `guardar()`, `listar()` y `eliminar()`
- `AccesoData` con métodos `guardar()`, `listar()`, `eliminar()` y `actualizarSalida()`
- Método privado `parsear()` en cada clase para reconstruir objetos desde línea de texto
- Creación automática del archivo si no existe al momento de la primera lectura
- Uso de `BufferedReader` para lectura eficiente línea por línea
- Uso de `BufferedWriter` con modo append (`true`) para escritura sin sobreescribir

### Formato de archivos generados

**`usuarios.txt`** — formato por línea:
```
ID,Nombre,Rol
```

**`accesos.txt`** — formato por línea:
```
idUsuario,fechaHoraEntrada,fechaHoraSalida
```
> El valor `null` en `fechaHoraSalida` indica que el usuario aún se encuentra dentro del laboratorio.

---

## [v1.0] — 2026-04-07 — Creación de entidades

### Descripción
Versión inicial del proyecto. Se definió la estructura base de la arquitectura por capas
y se implementaron las clases de entidad que representan los datos del dominio. Estas clases
son POJOs puros: no contienen lógica de negocio ni acceso a persistencia.

### Añadido
- Estructura de paquetes: `entidades`, `accesodatos`, `logicaNegocio`, `presentacion`
- `Rol.java` — enumeración con los valores `ESTUDIANTE` y `DOCENTE`
- `Usuario.java` — entidad con atributos `id`, `nombre` y `rol`, con constructor
  completo, getters, setters y `toString()` para serialización en archivo
- `Acceso.java` — entidad con atributos `idUsuario`, `fechaHoraEntrada` y
  `fechaHoraSalida`, con dos constructores: uno para entrada activa (salida `null`)
  y otro para registro completo

### Decisiones de diseño
- `fechaHoraSalida` se inicializa en `null` para representar que el usuario aún no ha salido
- El enum `Rol` evita el uso de cadenas arbitrarias para representar el tipo de usuario
- `toString()` en `Usuario` produce el formato directo de serialización usado por `AccesoDatos`

---

## Historial de commits

| Hash | Versión | Descripción |
|------|---------|-------------|
| pendiente | v2.0 | feat: interfaz gráfica JavaFX |
| `8611c5f` | docs | docs: agregar README, CHANGELOG y actualizar IA_USO |
| `88018db` | v1.3 | fix: validación de doble entrada |
| `31201d3` | v1.2 | feat: interfaz de usuario en consola |
| `ac02a7f` | v1.2 | feat: lógica de negocio y validaciones |
| `8790e79` | v1.1 | feat: implementación acceso a datos |
| `8fcd5a9` | v1.0 | feat: creación de entidades |
