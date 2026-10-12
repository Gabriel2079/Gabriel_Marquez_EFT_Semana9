package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Estudiante;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private final BibliotecaController controller;
    private final Usuario usuarioActual;

    private JButton btnSalir = new JButton("Cerrar Sesión");

    // --- Componentes Pestaña Libros ---
    private JTable tablaLibros;
    private DefaultTableModel modeloLibros;
    private JTextField txtId = new JTextField(4);
    private JTextField txtTitulo = new JTextField(12);
    private JTextField txtAutor = new JTextField(10);
    private JTextField txtIsbn = new JTextField(9);
    private JTextField txtStock = new JTextField(4);
    private JTextField txtEstudianteId = new JTextField(4);
    private JButton btnActualizarLibro = new JButton("Actualizar Libro");
    private JButton btnEliminarLibro = new JButton("Eliminar Libro");
    private JButton btnPrestar = new JButton("Realizar Préstamo");
    private JButton btnRecargarLibros = new JButton("Recargar Tabla");

    // --- Componentes Pestaña Estudiantes ---
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloEstudiantes;
    private JTextField txtEstId = new JTextField(4);
    private JTextField txtEstNombre = new JTextField(12);
    private JTextField txtEstRut = new JTextField(9);
    private JTextField txtEstCurso = new JTextField(8);
    private JTextField txtEstCorreo = new JTextField(12);
    private JButton btnGuardarEst = new JButton("Guardar");
    private JButton btnActualizarEst = new JButton("Actualizar");
    private JButton btnEliminarEst = new JButton("Eliminar");
    private JButton btnRecargarEst = new JButton("Recargar");
    private JButton btnLimpiarEst = new JButton("Limpiar");

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        this.controller = new BibliotecaController();

        configurarVentana();
        inicializarTabs();
        cargarDatosLibros();
        cargarDatosEstudiantes();
        configurarEventosLibros();
        configurarEventosEstudiantes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Biblioteca Escolar - " + (usuarioActual != null ? usuarioActual.getTipoPerfil() : "Sesión"));
        setSize(950, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void inicializarTabs() {
        // Barra superior con botón de salida
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperior.add(btnSalir);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Gestión de Libros y Préstamos", crearPanelLibros());
        tabbedPane.addTab("Gestión de Estudiantes", crearPanelEstudiantes());

        // Agregamos la barra superior al norte y las pestañas al centro
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Evento de salida con confirmación
        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea salir del sistema?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                dispose(); // Cierra la ventana liberando recursos
                System.exit(0); // Termina la ejecución de la JVM de forma limpia
            }
        });
    }


    private JPanel crearPanelLibros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        modeloLibros = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "ISBN", "Stock"}, 0);
        tablaLibros = new JTable(modeloLibros);
        panel.add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelCampos = new JPanel(new FlowLayout());
        panelCampos.add(new JLabel("ID Libro:")); panelCampos.add(txtId);
        panelCampos.add(new JLabel("Título:")); panelCampos.add(txtTitulo);
        panelCampos.add(new JLabel("Autor:")); panelCampos.add(txtAutor);
        panelCampos.add(new JLabel("ISBN:")); panelCampos.add(txtIsbn);
        panelCampos.add(new JLabel("Stock:")); panelCampos.add(txtStock);
        panelCampos.add(new JLabel("ID Alumno:")); panelCampos.add(txtEstudianteId);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnActualizarLibro);
        panelBotones.add(btnEliminarLibro);
        panelBotones.add(btnPrestar);
        panelBotones.add(btnRecargarLibros);

        panelInferior.add(panelCampos);
        panelInferior.add(panelBotones);
        panel.add(panelInferior, BorderLayout.SOUTH);

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaLibros.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modeloLibros.getValueAt(fila, 0).toString());
                txtTitulo.setText(modeloLibros.getValueAt(fila, 1).toString());
                txtAutor.setText(modeloLibros.getValueAt(fila, 2).toString());
                txtIsbn.setText(modeloLibros.getValueAt(fila, 3).toString());
                txtStock.setText(modeloLibros.getValueAt(fila, 4).toString());
            }
        });

        return panel;
    }

    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        modeloEstudiantes = new DefaultTableModel(new Object[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0);
        tablaEstudiantes = new JTable(modeloEstudiantes);
        panel.add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        txtEstId.setEditable(false);
        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelCampos = new JPanel(new FlowLayout());
        panelCampos.add(new JLabel("ID:")); panelCampos.add(txtEstId);
        panelCampos.add(new JLabel("Nombre:")); panelCampos.add(txtEstNombre);
        panelCampos.add(new JLabel("RUT:")); panelCampos.add(txtEstRut);
        panelCampos.add(new JLabel("Curso:")); panelCampos.add(txtEstCurso);
        panelCampos.add(new JLabel("Correo:")); panelCampos.add(txtEstCorreo);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardarEst);
        panelBotones.add(btnActualizarEst);
        panelBotones.add(btnEliminarEst);
        panelBotones.add(btnLimpiarEst);
        panelBotones.add(btnRecargarEst);

        panelInferior.add(panelCampos);
        panelInferior.add(panelBotones);
        panel.add(panelInferior, BorderLayout.SOUTH);

        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEstudiantes.getSelectedRow();
            if (fila >= 0) {
                txtEstId.setText(modeloEstudiantes.getValueAt(fila, 0).toString());
                txtEstNombre.setText(modeloEstudiantes.getValueAt(fila, 1).toString());
                txtEstRut.setText(modeloEstudiantes.getValueAt(fila, 2).toString());
                txtEstCurso.setText(modeloEstudiantes.getValueAt(fila, 3).toString());
                txtEstCorreo.setText(modeloEstudiantes.getValueAt(fila, 4).toString());
            }
        });

        return panel;
    }

    private void cargarDatosLibros() {
        modeloLibros.setRowCount(0);
        List<Libro> lista = controller.obtenerLibros();
        for (Libro l : lista) {
            modeloLibros.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getStock()});
        }
    }

    private void cargarDatosEstudiantes() {
        modeloEstudiantes.setRowCount(0);
        List<Estudiante> lista = controller.obtenerEstudiantes();
        for (Estudiante est : lista) {
            modeloEstudiantes.addRow(new Object[]{est.getId(), est.getNombre(), est.getRut(), est.getCurso(), est.getCorreo()});
        }
    }

    private void configurarEventosLibros() {
        btnActualizarLibro.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                String titulo = txtTitulo.getText().trim();
                String autor = txtAutor.getText().trim();
                String isbn = txtIsbn.getText().trim();

                boolean ok = controller.actualizarLibro(id, titulo, autor, isbn, "Editorial Duoc", stock, 1);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");
                    cargarDatosLibros();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el registro o no hubo cambios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valide que ID y Stock sean números válidos.");
            }
        });

        btnEliminarLibro.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                if (JOptionPane.showConfirmDialog(this, "¿Eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    boolean ok = controller.eliminarLibro(id);
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "Libro eliminado con éxito.");
                        cargarDatosLibros();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el libro.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro válido.");
            }
        });

        btnPrestar.addActionListener(e -> {
            try {
                int idLibro = Integer.parseInt(txtId.getText().trim());
                int idEstudiante = Integer.parseInt(txtEstudianteId.getText().trim());

                btnPrestar.setEnabled(false);

                controller.solicitarPrestamo(idEstudiante, idLibro, (exito, mensaje) -> {
                    btnPrestar.setEnabled(true);
                    cargarDatosLibros();
                    JOptionPane.showMessageDialog(this, mensaje, exito ? "Préstamo Procesado" : "Aviso",
                            exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                });
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el ID del Libro y el ID del Alumno.");
            }
        });

        btnRecargarLibros.addActionListener(e -> cargarDatosLibros());
    }

    private void configurarEventosEstudiantes() {
        btnGuardarEst.addActionListener(e -> {
            boolean ok = controller.agregarEstudiante(txtEstNombre.getText(), txtEstRut.getText(), txtEstCurso.getText(), txtEstCorreo.getText());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Estudiante guardado con éxito.");
                cargarDatosEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el estudiante.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnActualizarEst.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtEstId.getText().trim());
                boolean ok = controller.actualizarEstudiante(id, txtEstNombre.getText(), txtEstRut.getText(), txtEstCurso.getText(), txtEstCorreo.getText());
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente.");
                    cargarDatosEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el registro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
            }
        });

        btnEliminarEst.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtEstId.getText().trim());
                if (JOptionPane.showConfirmDialog(this, "¿Eliminar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    boolean ok = controller.eliminarEstudiante(id);
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                        cargarDatosEstudiantes();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el estudiante.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante válido.");
            }
        });

        btnRecargarEst.addActionListener(e -> cargarDatosEstudiantes());
        btnLimpiarEst.addActionListener(e -> limpiarCamposEstudiantes());
    }
    private void limpiarCamposEstudiantes() {
        txtEstId.setText("");
        txtEstNombre.setText("");
        txtEstRut.setText("");
        txtEstCurso.setText("");
        txtEstCorreo.setText("");
        tablaEstudiantes.clearSelection();
    }
}
