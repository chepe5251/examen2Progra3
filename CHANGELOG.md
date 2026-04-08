# Registro de Cambios — CHANGELOG

**Proyecto:** Sistema de Control de Acceso a Laboratorio  
**Lenguaje:** Java  
**Formato:** [Versionado Semántico](https://semver.org/lang/es/)

Todos los cambios relevantes de cada versión del proyecto se documentan en este archivo,
ordenados de la versión más reciente a la más antigua.

---

## [v1.3] — 2026-04-07 — Validaciones y pruebas

### Descripción
Se completó la cobertura de validaciones críticas del sistema. Se aisló la regla
de doble entrada en un método privado explícito dentro de `AccesoService`, mejorando
la legibilidad y el mantenimiento de la lógica de control de acceso.

### Corregido
- Se extrajo la validación de doble entrada al método privado `validarSinEntradaActiva()`
  en `AccesoService`, separando la regla de negocio de la orquestación del flujo principal.
- Se mejoró el mensaje de error para el caso de doble entrada, haciéndolo más
  descriptivo e identificable en tiempo de ejecución.

### Validaciones activas en esta versión
- Bloqueo de doble entrada sin salida previa registrada
- Bloqueo de salida sin entrada activa
- Rechazo de ID de usuario vacío o nulo en todos los métodos de servicio
- Rechazo de nombre vacío o nulo al registrar usuario
- Rechazo de rol nulo al registrar usuario
- Verificación de existencia del usuario antes de registrar cualquier acceso
- El cálculo de tiempo total excluye registros sin salida registrada

---

## [v1.2] — 2026-04-07 — Lógica de negocio e interfaz de usuario

### Descripción
Se implementaron las capas `LogicaNegocio` y `Presentacion`, completando la arquitectura
funcional del sistema. Toda la lógica de validación quedó centralizada en los servicios,
y la interacción con el usuario se delegó exclusivamente a `Main.java`.

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
- Todas las excepciones de negocio son capturadas en la capa de presentación
  y mostradas como mensajes de error al usuario

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

**`usuarios.txt`**
```
ID,Nombre,Rol
```

**`accesos.txt`**
```
idUsuario,fechaHoraEntrada,fechaHoraSalida
```
> El valor `null` en `fechaHoraSalida` indica que el usuario aún se encuentra dentro
> del laboratorio.

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
- `fechaHoraSalida` es de tipo `LocalDateTime` y se inicializa en `null` para
  representar que el usuario aún no ha salido del laboratorio
- El enum `Rol` evita el uso de cadenas arbitrarias para representar el tipo de usuario
- `toString()` en `Usuario` produce el formato directo de serialización usado por
  la capa `AccesoDatos`

---

## Historial de commits

| Hash | Versión | Descripción |
|------|---------|-------------|
| `88018db` | v1.3 | fix: validación de doble entrada |
| `31201d3` | v1.2 | feat: interfaz de usuario en consola |
| `ac02a7f` | v1.2 | feat: lógica de negocio y validaciones |
| `8790e79` | v1.1 | feat: implementación acceso a datos |
| `8fcd5a9` | v1.0 | feat: creación de entidades |
