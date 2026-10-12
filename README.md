# Sistema de Gestión de Biblioteca - Java Desktop Application

Aplicación de escritorio desarrollada en Java para la gestión integral de préstamos, usuarios y catálogo de libros en una biblioteca, implementando arquitectura multicapa (Layered Architecture), persistencia relacional SQL y control de concurrencia mediante hilos sincronizados.

---

## 🛠️ Tecnologías y Herramientas

* **Lenguaje:** Java (JDK 17 o superior)
* **IDE:** IntelliJ IDEA
* **Base de Datos:** MySQL / Oracle Database
* **Interfaz Gráfica:** Java Swing / AWT
* **Control de Versiones:** Git & GitHub

---

## 📁 Estructura del Proyecto

El código fuente sigue el patrón de separación de responsabilidades en capas dentro del directorio `src/main/java`:

* `config`: Configuración de conexión y parámetros hacia la base de datos relacional.
* `model`: Clases de entidad del dominio de negocio (encapsulamiento y constructores).
* `dao`: Capa de acceso a datos (Data Access Object) con operaciones CRUD y persistencia SQL.
* `service`: Lógica de negocio, validaciones y reglas de préstamo.
* `controller`: Coordinación y mediación entre los eventos visuales y la capa de servicio.
* `view`: Formularios, vistas y componentes gráficos de la interfaz de usuario.

---

## ⚙️ Concurrencia y Sincronización

La aplicación procesa transacciones de préstamo en segundo plano utilizando hilos (`Thread` / `Runnable`). Para evitar condiciones de carrera (*race conditions*) y asegurar la integridad del inventario de libros cuando ocurren solicitudes simultáneas, se implementó el control de concurrencia mediante la palabra clave **`synchronized`** en la sección crítica de validación y actualización del estado del ejemplar.

---

## 🚀 Puesta en Marcha

### 1. Base de Datos
Ejecutar los scripts SQL proporcionados en la raíz del proyecto en el siguiente orden:
1. `Script_crea_tablas_biblioteca.sql` (creación de tablas, claves primarias y foráneas).
2. `Script_poblado_tablas_biblioteca.sql` (inserción de registros iniciales de prueba).

Configurar las credenciales de conexión en la clase del paquete `config`.

### 2. Ejecución de la Aplicación
1. Clonar el repositorio:
   ```bash
   git clone <URL_DE_TU_REPOSITORIO>
