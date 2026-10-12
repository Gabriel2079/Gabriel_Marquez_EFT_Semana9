package com.biblioteca.service;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.PrestamoDAO;
import com.biblioteca.model.Prestamo;

import javax.swing.SwingUtilities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrestamoService {

    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    public PrestamoService() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
    }

    public interface PrestamoCallback {
        void onResultado(boolean exito, String mensaje);
    }

    public void registrarPrestamoAsync(int idEstudiante, int idLibro, PrestamoCallback callback) {
        new Thread(() -> {
            boolean exitoFinal = false;
            String mensaje;

            try {
                // Simulación breve de procesamiento para apreciar el hilo en segundo plano
                Thread.sleep(400);

                // 1. Control concurrente sincronizado para asegurar stock
                boolean stockActualizado = libroDAO.descontarStock(idLibro);

                if (stockActualizado) {
                    // 2. Cálculo automático de fechas (7 días de plazo)
                    LocalDate hoy = LocalDate.now();
                    LocalDate devolucion = hoy.plusDays(7);
                    Prestamo prestamo = new Prestamo(0, idEstudiante, idLibro, hoy, devolucion, false);

                    boolean prestamoInsertado = prestamoDAO.registrarPrestamo(prestamo);

                    if (prestamoInsertado) {
                        exitoFinal = true;
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        mensaje = "Préstamo registrado exitosamente.\nFecha de vencimiento: " + devolucion.format(formato);
                    } else {
                        mensaje = "Error al asentar el registro del préstamo en la base de datos.";
                    }
                } else {
                    mensaje = "Operación rechazada: No hay ejemplares disponibles en stock.";
                }

            } catch (InterruptedException e) {
                mensaje = "Error de ejecución en segundo plano: " + e.getMessage();
                Thread.currentThread().interrupt();
            }

            // Regreso seguro al hilo gráfico Swing
            final boolean finalExito = exitoFinal;
            final String finalMsg = mensaje;
            SwingUtilities.invokeLater(() -> callback.onResultado(finalExito, finalMsg));

        }).start();
    }
}