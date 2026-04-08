# Documentación de Uso de Inteligencia Artificial

**Proyecto:** Sistema de Control de Acceso a Laboratorio  
**Lenguaje:** Java  
**Arquitectura:** Capas (Entidades · AccesoDatos · LogicaNegocio · Presentacion)  
**Herramienta IA utilizada:** Claude (Anthropic) — claude-sonnet-4-6  

---

## 1. Prompts Utilizados

### Prompt 1 — Estructura base del proyecto

> "Actúa como un desarrollador senior Java experto en arquitectura por capas.
> Crea la base de un sistema en Java llamado 'Sistema de Control de Acceso a Laboratorio'
> cumpliendo estrictamente arquitectura en capas: Entidades, AccesoDatos, LogicaNegocio,
> Presentacion. La capa Presentacion NO puede acceder directamente a AccesoDatos.
> Persistencia en archivos .txt. Sin base de datos."

**Resultado generado:**
- Estructura completa de paquetes
- Clases base en todas las capas con atributos, constructores y getters/setters
- Archivo inicial `Menu.java` con menú funcional en consola

---

### Prompt 2 — Capa Entidades

> "Crea las clases de la capa Entidades para un sistema de control de acceso a laboratorio
> en Java. Clases: Usuario (id, nombre, rol) y Acceso (idUsuario, fechaHoraEntrada,
> fechaHoraSalida). Solo POJOs, con constructores, getters y setters. Sin lógica de negocio."

**Resultado generado:**
- `Rol.java` — enum con valores `ESTUDIANTE` y `DOCENTE`
- `Usuario.java` — POJO con tres atributos y `toString()` para serialización
- `Acceso.java` — POJO con dos constructores (entrada activa / registro completo)

---

### Prompt 3 — Capa AccesoDatos

> "Crea la capa AccesoDatos en Java para manejar persistencia en archivos .txt.
> Clases: UsuarioData y AccesoData. Usar BufferedReader y BufferedWriter.
> Métodos básicos: guardar, listar, eliminar. Sin lógica de negocio ni validaciones complejas."

**Resultado generado:**
- `UsuarioData.java` — lectura y escritura en `usuarios.txt`
- `AccesoData.java` — lectura, escritura y actualización de salida en `accesos.txt`
- Método `actualizarSalida()` para registrar la hora de salida sobre un acceso activo

---

### Prompt 4 — Capa LogicaNegocio

> "Crea la capa LogicaNegocio en Java. Clases: UsuarioService y AccesoService.
> UsuarioService: validar IDs duplicados y datos vacíos.
> AccesoService: validar no doble entrada, no salida sin entrada, calcular tiempo
> dentro del laboratorio. Esta capa usa AccesoDatos. Aquí van TODAS las validaciones."

**Resultado generado:**
- `UsuarioService.java` — validaciones de campos vacíos e IDs duplicados
- `AccesoService.java` — validaciones de flujo de acceso y cálculo de tiempo total
- Método `tieneEntradaActiva()` como auxiliar privado de validación

---

### Prompt 5 — Capa Presentacion (consola)

> "Crea la capa Presentacion con un menú en consola en Java. Funciones: registrar usuario,
> ver usuarios, registrar entrada, registrar salida, ver reportes. SOLO puede usar
> LogicaNegocio. No acceder directamente a AccesoDatos. Clase Main con menú interactivo
> usando Scanner."

**Resultado generado:**
- `Main.java` — menú principal con submenú de reportes
- Métodos privados por funcionalidad (`registrarUsuario`, `verReportes`, etc.)
- Método auxiliar `leerEntero()` para manejo seguro de entrada numérica

---

### Prompt 6 — Archivo IA_USO.md

> "Genera un archivo IA_USO.md para un proyecto Java. Debe incluir: prompts utilizados,
> qué parte del sistema generó cada prompt, ajustes manuales realizados y justificación
> técnica del uso de IA. Formato profesional en Markdown."

**Resultado generado:**
- `IA_USO.md` — documentación completa del uso de IA con 4 secciones estructuradas

---

### Prompt 7 — Diagrama Mermaid de arquitectura

> "Genera un diagrama simple en Mermaid para incluir dentro del README.md que represente
> la arquitectura por capas: Presentacion, LogicaNegocio, AccesoDatos, Entidades.
> Presentacion solo puede comunicarse con LogicaNegocio. No debe existir relación directa
> entre Presentacion y AccesoDatos."

**Resultado generado:**
- Bloque `mermaid` con `flowchart TD`, flechas etiquetadas y estilos de color por capa
- Integrado directamente en `README.md` dentro de la sección "Diagrama de Arquitectura"

---

### Prompt 8 — CHANGELOG.md

> "Genera un archivo CHANGELOG.md en español para el proyecto. Versiones obligatorias:
> v1.0 creación de entidades, v1.1 implementación de acceso a datos, v1.2 lógica de negocio,
> v1.3 validaciones y pruebas. Para cada versión: descripción, lista de cambios y
> coherencia con el proyecto."

**Resultado generado:**
- `CHANGELOG.md` — cuatro versiones documentadas con secciones Añadido / Corregido
- Tabla final de historial de commits con hashes reales del repositorio

---

### Prompt 9 — Revisión y mejora del README.md

> "Revisa el README.md de mi proyecto Java universitario y mejóralo sin cambiar el propósito
> del sistema. Verifica claridad de redacción, ortografía, orden lógico de secciones,
> coherencia técnica con arquitectura por capas y que el diagrama Mermaid esté bien
> integrado."

**Resultado generado:**
- `README.md` revisado con correcciones de estructura, redacción y coherencia técnica

---

### Prompt 10 — Interfaz gráfica con JavaFX

> "Quiero transformar mi proyecto Java en una aplicación de escritorio con interfaz gráfica
> moderna usando JavaFX. Mantener arquitectura por capas. Dashboard principal, menú lateral
> elegante, módulos de gestión de usuarios, accesos y reportes. Tablas, formularios,
> alertas visuales. Colores: azul oscuro para navegación, verde para acciones correctas,
> rojo para errores. Presentacion solo consume LogicaNegocio."

**Resultado generado:**
- `MainApp.java` — punto de entrada JavaFX con `Stage` configurado (1150×730)
- `MainController.java` — ventana principal con sidebar azul oscuro (`#0f2744`) y área central dinámica
- `DashboardController.java` — panel con 3 tarjetas de métricas, info del sistema y reglas de política
- `UsuariosController.java` — formulario de registro en fila + `TableView` con badges de rol y botón eliminar
- `AccesosController.java` — dos tarjetas paralelas (Entrada / Salida) con retroalimentación de estado inline
- `ReportesController.java` — búsqueda por ID, tabla con duración calculada y tarjetas de resumen
- `EstilosUI.java` — paleta centralizada, métodos de estilo, hover en botones y alertas estandarizadas
- `AccesoService.java` modificado — se añadieron `listarTodosLosAccesos()` y `contarDentroDelLaboratorio()`

---

## 2. Parte del Sistema Generada por Cada Prompt

| Prompt | Área | Archivos generados o modificados |
|--------|------|----------------------------------|
| 1 | Todas las capas (esqueleto) | Estructura de paquetes + clases iniciales |
| 2 | `entidades` | `Rol.java`, `Usuario.java`, `Acceso.java` |
| 3 | `accesodatos` | `UsuarioData.java`, `AccesoData.java` |
| 4 | `logicaNegocio` | `UsuarioService.java`, `AccesoService.java` |
| 5 | `presentacion` | `Main.java` |
| 6 | Documentación | `IA_USO.md` |
| 7 | Documentación | `README.md` (sección Diagrama de Arquitectura) |
| 8 | Documentación | `CHANGELOG.md` |
| 9 | Documentación | `README.md` (revisión y mejora general) |
| 10 | `presentacion` (JavaFX) | `MainApp.java`, `MainController.java`, `DashboardController.java`, `UsuariosController.java`, `AccesosController.java`, `ReportesController.java`, `EstilosUI.java` |

---

## 3. Ajustes Manuales Realizados

### Capa Entidades

- Se renombró `RegistroAcceso` a `Acceso` para simplificar el nombre y alinearlo con el dominio.
- Se eliminó el método `estaActivo()` de `Acceso.java`, ya que constituía lógica de negocio
  dentro de una entidad, violando el principio de responsabilidad única.

### Capa AccesoDatos

- Se verificó que `actualizarSalida()` en `AccesoData` solo modifique el primer registro activo
  por usuario, evitando sobreescrituras en escenarios con historial extenso.
- Se ajustó el formato de serialización de `LocalDateTime` para compatibilidad con
  `LocalDateTime.parse()` al leer desde archivo (formato ISO-8601 nativo de Java).

### Capa LogicaNegocio

- Se extrajo `validarUsuarioExiste()` como método privado reutilizable en `AccesoService`,
  evitando duplicación de la consulta a `UsuarioData` dentro de los métodos públicos.
- Se confirmó que `calcularTiempoTotalEnMinutos()` excluya registros sin salida (`null`),
  para no generar errores de cálculo con accesos activos.
- Se añadieron `listarTodosLosAccesos()` y `contarDentroDelLaboratorio()` para servir
  métricas al Dashboard sin exponer `AccesoDatos` a la capa de presentación.

### Capa Presentacion — Consola

- Se separó el submenú de reportes en el método `verReportes()` para evitar que el `switch`
  principal creciera en complejidad.
- Se añadió `leerRol()` como utilidad privada para centralizar la selección del enum `Rol`.
- Se eliminaron acentos en los mensajes de consola para compatibilidad en terminales Windows
  sin configuración UTF-8 explícita.

### Capa Presentacion — JavaFX

- Se eliminó el import no utilizado `javafx.scene.shape.Circle` en `AccesosController.java`
  detectado durante la revisión del código.
- Se verificó que ningún controlador JavaFX importe ni use clases de `AccesoDatos` directamente.
- Se validó que `EstilosUI.java` no contenga lógica de negocio, solo constantes y utilidades
  de presentación.

### Documentación

- El README fue actualizado para reflejar el modo dual de ejecución (JavaFX y consola),
  con instrucciones separadas para cada uno.
- El CHANGELOG fue extendido con la versión `v2.0` documentando todos los archivos añadidos
  y modificados durante la migración a JavaFX.
- Se eliminaron el doble separador `---` duplicado que existía en `IA_USO.md`.

---

## 4. Justificación Técnica del Uso de IA

### 4.1 Generación de estructura repetitiva

Las clases POJO (`Usuario`, `Acceso`) y las clases DAO (`UsuarioData`, `AccesoData`) siguen
patrones altamente repetitivos. La IA permitió generar esta base estructural de forma
consistente y sin errores de tipeo, liberando tiempo para las decisiones de diseño.

### 4.2 Respeto de la arquitectura por capas

Se instruyó a la IA con restricciones arquitectónicas explícitas en cada prompt. Esto permitió
validar en tiempo real que cada clase generada respetara las dependencias correctas, reduciendo
el riesgo de acoplamientos incorrectos. En la interfaz JavaFX, la restricción
*"Presentacion NO puede acceder directamente a AccesoDatos"* fue aplicada en los 6 controladores.

### 4.3 Diseño de interfaz moderno con JavaFX puro

Para la interfaz gráfica se optó por JavaFX sin FXML, usando estilos inline `-fx-`. La IA
generó los patrones visuales (tarjetas, badges de color, sidebar, tablas con `CellFactory`)
de forma coherente y consistente, aplicando la misma paleta de colores en todos los módulos.

### 4.4 Coherencia entre capas

Al proporcionar contexto acumulado en cada prompt, la IA generó código compatible con las
firmas de métodos existentes sin necesidad de adaptar tipos de retorno manualmente.

### 4.5 Limitaciones identificadas

| Limitación | Solución aplicada |
|------------|-------------------|
| `Acceso.java` incluía lógica de negocio (`estaActivo()`) | Se eliminó manualmente |
| Nombre `RegistroAcceso` no alineado con el dominio | Se renombró a `Acceso` |
| Acentos en strings de consola con problemas en Windows | Se eliminaron manualmente |
| README con dos diagramas Mermaid redundantes | Se consolidaron en uno |
| Tabla de capas con nombres en minúsculas inconsistentes | Se corrigieron a `Entidades`, etc. |
| Sección Notas con texto de plantilla sin contenido real | Se reemplazó por notas técnicas |
| Dashboard requería métodos inexistentes en `AccesoService` | Se añadieron los métodos necesarios |

### 4.6 Conclusión

El uso de IA en este proyecto fue una herramienta de apoyo para la generación de código
estructural, repetitivo y de diseño visual. Las decisiones de arquitectura, la revisión de
reglas de negocio, la validación de separación de capas y todos los ajustes de calidad
fueron realizados por el desarrollador. La IA no reemplazó el criterio técnico, sino que
aceleró significativamente la producción del código base y la documentación del proyecto.

---

*Documento generado como parte de la entrega del proyecto.*  
*Fecha: 2026-04-07*
