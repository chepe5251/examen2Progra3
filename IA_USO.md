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

### Prompt 5 — Capa Presentacion

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

---

---

## 3. Ajustes Manuales Realizados

### Capa Entidades

- Se renombró `RegistroAcceso` a `Acceso` para simplificar el nombre de la clase y alinearlo
  con la convención del dominio del problema.
- Se eliminó el método `estaActivo()` de la clase `Acceso`, ya que constituía lógica
  de negocio dentro de una clase de entidad, violando el principio de responsabilidad única.

### Capa AccesoDatos

- Se verificó que el método `actualizarSalida()` en `AccesoData` solo modifique el primer
  registro activo encontrado por usuario, evitando sobreescrituras no deseadas en
  escenarios con historial extenso.
- Se ajustó el formato de serialización de `LocalDateTime` para garantizar compatibilidad
  con `LocalDateTime.parse()` al leer desde archivo (formato ISO-8601 nativo de Java).

### Capa LogicaNegocio

- Se extrajo `validarUsuarioExiste()` como método privado reutilizable en `AccesoService`,
  evitando duplicación de la consulta a `UsuarioData` dentro de los métodos públicos.
- Se confirmó que `calcularTiempoTotalEnMinutos()` excluya registros sin salida (`null`),
  para no generar errores de cálculo con accesos aún activos.

### Capa Presentacion

- Se separó el submenú de reportes en un método propio `verReportes()` para evitar
  que el `switch` principal creciera en complejidad.
- Se añadió el método `leerRol()` como utilidad privada para centralizar la selección
  del enum `Rol` y retornar `null` ante entrada inválida sin lanzar excepción.
- Se eliminaron acentos en los mensajes de consola para asegurar compatibilidad de
  codificación en terminales Windows sin configuración UTF-8 explícita.

---

## 4. Justificación Técnica del Uso de IA

### 4.1 Generación de estructura repetitiva

Las clases POJO (`Usuario`, `Acceso`) y las clases DAO (`UsuarioData`, `AccesoData`) siguen
patrones altamente repetitivos: atributos privados, constructores, getters/setters y métodos
de lectura/escritura en archivo. La IA permitió generar esta base estructural de forma
consistente y sin errores de tipeo, liberando tiempo para enfocarse en las decisiones
de diseño.

### 4.2 Respeto de la arquitectura por capas

Se instruyó a la IA con restricciones arquitectónicas explícitas en cada prompt
(por ejemplo: *"Presentacion NO puede acceder directamente a AccesoDatos"*). Esto permitió
validar en tiempo real que cada clase generada respetara las dependencias correctas entre
capas, reduciendo el riesgo de acoplamientos incorrectos.

### 4.3 Coherencia entre capas

Al proporcionar contexto acumulado en cada prompt (las clases de capas anteriores ya
creadas), la IA generó código compatible sin necesidad de adaptar firmas de métodos
ni tipos de retorno manualmente.

### 4.4 Limitaciones identificadas

| Limitación | Solución aplicada |
|------------|-------------------|
| La IA incluyó lógica de negocio dentro de `Acceso.java` (`estaActivo()`) | Se eliminó manualmente |
| Nombre de clase `RegistroAcceso` no alineado con el dominio | Se renombró a `Acceso` |
| Acentos en strings pueden causar problemas en consolas Windows | Se eliminaron manualmente |
| El README generado contenía dos diagramas Mermaid redundantes | Se consolidaron en uno dentro de la sección "Diagrama de Arquitectura" |
| La tabla de capas usaba nombres en minúsculas inconsistentes con el código | Se corrigieron a `Entidades`, `AccesoDatos`, etc. |
| La sección Notas del README contenía texto de plantilla sin contenido real | Se reemplazó por notas técnicas concretas |

### 4.5 Conclusión

El uso de IA en este proyecto fue una herramienta de apoyo para la generación de código
estructural y repetitivo. Las decisiones de diseño, la revisión de reglas de negocio,
la validación de la arquitectura y los ajustes de compatibilidad fueron realizados
por el desarrollador. La IA no reemplazó el criterio técnico, sino que aceleró la
producción del código base.

---

*Documento generado como parte de la entrega del proyecto.*  
*Fecha: 2026-04-07*
