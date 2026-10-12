package com.biblioteca.controller;

import com.biblioteca.dao.EstudianteDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Estudiante;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.PrestamoService;

import java.util.List;

public class BibliotecaController {

    private final LibroDAO libroDAO;
    private final EstudianteDAO estudianteDAO;
    private final UsuarioDAO usuarioDAO;
    private final PrestamoService prestamoService;

    public BibliotecaController() {
        this.libroDAO = new LibroDAO();
        this.estudianteDAO = new EstudianteDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.prestamoService = new PrestamoService();
    }

    // --- AUTENTICACIÓN ---
    public Usuario login(String correo, String contrasena) {
        if (correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            return null;
        }
        return usuarioDAO.autenticar(correo.trim(), contrasena.trim());
    }

    // --- CRUD LIBROS ---
    public List<Libro> obtenerLibros() {
        return libroDAO.listarTodos();
    }

    public boolean agregarLibro(String titulo, String autor, String isbn, String editorial, int stock, int idCategoria) {
        if (titulo == null || titulo.trim().isEmpty() || stock < 0) return false;
        return libroDAO.insertar(new Libro(0, titulo.trim(), autor.trim(), isbn.trim(), editorial.trim(), stock, idCategoria));
    }

    public boolean actualizarLibro(int id, String titulo, String autor, String isbn, String editorial, int stock, int idCategoria) {
        if (id <= 0 || titulo == null || titulo.trim().isEmpty() || stock < 0) return false;
        return libroDAO.actualizar(new Libro(id, titulo.trim(), autor.trim(), isbn.trim(), editorial.trim(), stock, idCategoria));
    }

    public boolean eliminarLibro(int id) {
        if (id <= 0) return false;
        return libroDAO.eliminar(id);
    }

    // --- CRUD ESTUDIANTES ---
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteDAO.listarTodos();
    }

    public boolean agregarEstudiante(String nombre, String rut, String curso, String correo) {
        if (nombre == null || nombre.trim().isEmpty() || rut == null || rut.trim().isEmpty()) return false;
        return estudianteDAO.insertar(new Estudiante(0, nombre.trim(), rut.trim(), curso.trim(), correo.trim()));
    }

    public boolean actualizarEstudiante(int id, String nombre, String rut, String curso, String correo) {
        if (id <= 0 || nombre == null || nombre.trim().isEmpty()) return false;
        return estudianteDAO.actualizar(new Estudiante(id, nombre.trim(), rut.trim(), curso.trim(), correo.trim()));
    }

    public boolean eliminarEstudiante(int id) {
        if (id <= 0) return false;
        return estudianteDAO.eliminar(id);
    }

    // --- PRÉSTAMO CONCURRENTE ---
    public void solicitarPrestamo(int idEstudiante, int idLibro, PrestamoService.PrestamoCallback callback) {
        if (idEstudiante <= 0 || idLibro <= 0) {
            callback.onResultado(false, "El ID del estudiante y del libro deben ser mayores a 0.");
            return;
        }
        prestamoService.registrarPrestamoAsync(idEstudiante, idLibro, callback);
    }
}