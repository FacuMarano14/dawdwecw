# 🏞️ Sistema de Gestión de Excursiones y Reservas

---

## Alumno

**Nombre del alumno**: _[nombre_del_alumno]_

## 📄 Descripción del Proyecto

Este sistema gestiona excursiones y reservas, aplicando conceptos avanzados de Programación Orientada a Objetos (POO) en Java. Permite la administración de excursiones con actividades personalizadas, cálculo de precios dinámicos, y manejo de reservas, asegurando persistencia de datos mediante serialización.

### Objetivos

- Implementar un modelo de gestión robusto para excursiones y reservas.
- Aplicar validaciones y manejar excepciones en los datos.
- Persistir información en archivos utilizando serialización.

## 🛠️ Funcionalidades del Sistema

### 1. Gestión de Excursiones

- Crear excursiones personalizables con actividades.
- Calcular precios dinámicos basados en tipo, dificultad, y costos adicionales.
- Consultar excursiones según precio o ID.

### 2. Gestión de Reservas

- Realizar y gestionar reservas para clientes.
- Validar cupos disponibles y confirmar pagos.
- Calcular ingresos totales por reservas confirmadas.

### 3. Persistencia de Datos

- Almacenar y recuperar excursiones y reservas mediante archivos binarios.
- Gestionar archivos de datos por ID, con rutas automáticas generadas.

---

## 📦 Estructura del Proyecto

### **Capa Modelo**

#### 1. **Excursion** _(Clase Abstracta)_

- **Atributos**:
  - `id`: Identificador único que debe incrementarse automáticamente, comenzando en 1, controlada por `ExcursionRepository`.
  - `titulo`: Título descriptivo de la excursión (mínimo 3 caracteres).
  - `precioBase`: Precio base de la excursión.
  - `cupoMaximo`: Máximo número de reservas permitidas (no puede ser negativo).
  - `transient GestorReservas gestorReservas`: Objeto encargado de gestionar las reservas asociadas a la excursión, se instancia en el constructor.
  - `List<String> actividades`: Lista de actividades incluidas en la excursión.

- **Métodos Clave**:
  - `boolean realizarReserva(String cliente)`: Realiza una reserva si hay cupos disponibles, gestionada por el `gestorReservas`.
  - `abstract double calcularPrecioFinal()`: Cálculo del precio final según el tipo de excursión.
  - `void agregarActividad(String actividad)`: Permite agregar actividades personalizadas.
  - `String mostrarResumen()`: Resumen detallado de la excursión.
  - `private String generarFilePath()`: Crea una ruta basada en el ID para persistir las reservas (ej. "reservas_excursion_{id}.dat").
  - `Optional<List<Reserva>> traerReservas()`: Devuelve las reservas de una excursión.

#### 2. **ExcursionAventura** _(Subclase de Excursion)_

- **Atributos**:
  - `int nivelDificultad`: Nivel de dificultad de la excursión (1 a 5).
  - `double seguroAdicional`: Costo adicional por seguro.

- **Métodos Clave**:
  - `calcularPrecioFinal()`: Aplica un incremento del 10% al precio base por cada nivel de dificultad.
  - `String consejosSeguridad()`: Devuelve consejos de seguridad según el nivel de dificultad.

#### 3. **Reserva** _(Implementa Serializable)_

- **Atributos**:
  - `int idExcursion`: Identificador de la excursión reservada.
  - `String cliente`: Nombre del cliente que realiza la reserva.
  - `String fechaReserva`: Fecha de la reserva.
  - `double precio`: Precio final de la reserva.
  - `boolean pagoConfirmado`: Indica si el pago está confirmado.

- **Métodos Clave**:
  - `boolean confirmarPago()`: Marca la reserva como pagada.
  - `String detalleReserva()`: Devuelve los detalles de la reserva.

---

### 👨‍💼 **Capa Negocio**

#### 1. **GestorExcursiones**

- Gestiona las excursiones utilizando un repositorio persistente.

- **Atributos**:
  - `Repository<T> repository`: Repositorio que almacena y gestiona las excursiones.

- **Métodos Públicos**:
  - `boolean agregarExcursion(Excursion excursion)`: Agrega una excursión al repositorio.
  - `Optional<Excursion> buscarPorId(int id)`: Busca una excursión por su ID.
  - `List<Excursion> filtrarExcursiones(Predicate<Excursion> criterio)`: Filtra excursiones según un criterio.
  - `List<Excursion> consultarExcursionesPorPrecio(double precioMin)`: Consulta excursiones cuyo precio final sea mayor o igual al valor proporcionado.

#### 2. **GestorReservas**

- Gestiona las reservas utilizando un repositorio persistente.

- **Atributos**:
  - `Repository<T> repository`: Repositorio que almacena y gestiona las reservas.
  - `final int cupoMaximo`: Máximo número de reservas permitidas.

- **Métodos Públicos**:
  - `boolean realizarReserva(Reserva reserva)`: Realiza y guarda una reserva si hay cupo disponible.
  - `List<Reserva> getReservas()`: Devuelve todas las reservas.
  - `double calcularIngresos(Predicate<Reserva> filtro)`: Calcula ingresos específicos utilizando un predicado.
  - `double calcularIngresosTotales()`: Calcula los ingresos totales de reservas confirmadas.

---

### 📝 **Capa Persistencia**

#### 1. **Repository<T>** _(Interfaz Genérica)_

Define operaciones genéricas: agregar, eliminar, buscar, listar.

#### 2. **ExcursionRepository y ReservaRepository**

- Implementan las operaciones del repositorio con persistencia en archivos binarios.

#### 3. **Serializer<T> y GestorPersistencia**

- Manejan serialización y deserialización de datos en archivos `.dat`.


---
## Para poder implementar `consejosSeguridad()`

#### Consejos de seguridad para cada nivel(en el método solo debes de devolver uno por nivel):
```JavaScript
Nivel 1: Muy Fácil
["Mantén una buena hidratación",
 "Usa ropa cómoda y ligera",
 "Aplica protector solar",
 "Usa calzado adecuado"]

Nivel 2: Fácil
["No camines solo, mantén el grupo",
 "Familiarízate con la ruta",
 "Lleva un botiquín básico",
 "Respeta el entorno natural"]

Nivel 3: Moderado
["Mantén contacto con el guía",
 "Haz estiramientos antes de caminar",
 "Revisa el clima antes de salir",
 "Evita la fatiga, descansa regularmente"]

Nivel 4: Difícil
["Usa equipo especializado y verifica su estado",
 "Monitorea las condiciones físicas del grupo",
 "Toma descansos regulares",
 "Prepárate para emergencias"]

Nivel 5: Muy Difícil
["Asegúrate de estar en buena condición física",
 "Actúa rápidamente ante incidentes",
 "Prepárate para cambios climáticos drásticos",
 "Revisa todo el equipo antes de salir"]

```
