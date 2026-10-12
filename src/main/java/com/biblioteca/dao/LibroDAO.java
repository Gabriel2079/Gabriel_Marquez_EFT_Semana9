package com.biblioteca.dao;

import com.biblioteca.config.DatabaseConnection;
import com.biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // 1. CREATE / INSERT
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }

    // 2. UPDATE con comprobación booleana
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, id_categoria = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            ps.setInt(7, libro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    // 3. DELETE con comprobación booleana
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    // 4. READ / SELECT ALL
    public List<Libro> listarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        rs.getInt("id_categoria")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    // 5. Concurrencia segura: synchronized contra condiciones de carrera
    public synchronized boolean descontarStock(int idLibro) {
        String sql = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al descontar stock: " + e.getMessage());
            return false;
        }
    }
}