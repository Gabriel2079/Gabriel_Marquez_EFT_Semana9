package com.biblioteca.dao;

import com.biblioteca.config.DatabaseConnection;
import com.biblioteca.model.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public boolean insertar(Estudiante est) {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, est.getNombre());
            ps.setString(2, est.getRut());
            ps.setString(3, est.getCurso());
            ps.setString(4, est.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Estudiante est) {
        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, est.getNombre());
            ps.setString(2, est.getRut());
            ps.setString(3, est.getCurso());
            ps.setString(4, est.getCorreo());
            ps.setInt(5, est.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    public List<Estudiante> listarTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("curso"),
                        rs.getString("correo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
        }
        return lista;
    }
}