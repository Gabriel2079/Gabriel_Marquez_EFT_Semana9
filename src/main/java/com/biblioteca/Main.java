package com.biblioteca;

import com.biblioteca.model.Usuario;
import com.biblioteca.view.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Usuario bibliotecario de prueba para la interfaz
            Usuario usuarioAdmin = new Usuario(1, "Administrador Biblioteca", "11.111.111-1", "admin@duoc.cl", "1234", "bibliotecario");
            VentanaPrincipal ventana = new VentanaPrincipal(usuarioAdmin);
            ventana.setVisible(true);
        });
    }
}