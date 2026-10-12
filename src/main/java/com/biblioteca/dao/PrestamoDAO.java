package com.biblioteca.dao;

import com.biblioteca.config.DatabaseConnection;
import com.biblioteca.model.Prestamo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrestamoDAO {

    public boolean registrarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prestamo.getIdEstudiante());
            ps.setInt(2, prestamo.getIdLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            ps.setBoolean(5, prestamo.isDevuelto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar préstamo: " + e.getMessage());
            return false;
        }
    }
}