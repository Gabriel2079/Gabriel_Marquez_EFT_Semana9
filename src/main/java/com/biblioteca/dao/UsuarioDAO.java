package com.biblioteca.dao;

import com.biblioteca.config.DatabaseConnection;
import com.biblioteca.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("rut"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }
}