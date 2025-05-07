/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futapp;

/**
 *
 * @author fernando.pedridomarino
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class PanelConvocatorias extends JPanel {
    private JTable tablaConvocatorias;
    private DefaultTableModel modeloConvocatorias;
    private JTable tablaJugadores;
    private DefaultTableModel modeloJugadores;
    private JTable tablaSeleccionados;
    private DefaultTableModel modeloSeleccionados;
    private JTextField tfNombreConvocatoria;
    private JSpinner spinnerFecha;
    private JButton btnAgregarJugador, btnRemoverJugador, btnGuardarConvocatoria;

    public PanelConvocatorias() {
        setLayout(new BorderLayout());

        // Panel superior: Crear convocatoria
        JPanel panelSuperior = new JPanel(new GridLayout(2, 3));
        tfNombreConvocatoria = new JTextField();
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd");
        spinnerFecha.setEditor(editor);

        panelSuperior.add(new JLabel("Nombre de la Convocatoria:"));
        panelSuperior.add(new JLabel("Fecha:"));
        panelSuperior.add(new JLabel(""));
        panelSuperior.add(tfNombreConvocatoria);
        panelSuperior.add(spinnerFecha);
        panelSuperior.add(new JLabel(""));

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: tablas de jugadores y jugadores seleccionados
        JPanel panelCentral = new JPanel(new GridLayout(1, 2));

        // Tabla de jugadores disponibles
        modeloJugadores = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición", "Dorsal"}, 0);
        tablaJugadores = new JTable(modeloJugadores);
        JScrollPane scrollJugadores = new JScrollPane(tablaJugadores);
        panelCentral.add(scrollJugadores);

        // Tabla de jugadores seleccionados
        modeloSeleccionados = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición", "Dorsal"}, 0);
        tablaSeleccionados = new JTable(modeloSeleccionados);
        JScrollPane scrollSeleccionados = new JScrollPane(tablaSeleccionados);
        panelCentral.add(scrollSeleccionados);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior: botones de acción
        JPanel panelInferior = new JPanel(new GridLayout(1, 3));
        btnAgregarJugador = new JButton("Agregar a Convocatoria");
        btnRemoverJugador = new JButton("Remover de Convocatoria");
        btnGuardarConvocatoria = new JButton("Guardar Convocatoria");

        panelInferior.add(btnAgregarJugador);
        panelInferior.add(btnRemoverJugador);
        panelInferior.add(btnGuardarConvocatoria);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar jugadores y convocatorias
        cargarJugadores();
        cargarConvocatorias();

        // Eventos
        btnAgregarJugador.addActionListener(e -> agregarJugadorASeleccionados());
        btnRemoverJugador.addActionListener(e -> removerJugadorDeSeleccionados());
        btnGuardarConvocatoria.addActionListener(e -> guardarConvocatoria());
    }

    // Método para cargar jugadores disponibles
    private void cargarJugadores() {
        modeloJugadores.setRowCount(0);
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, nombre, posicion, dorsal FROM jugadores")) {
            while (rs.next()) {
                modeloJugadores.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal")
                });
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para cargar convocatorias existentes
    private void cargarConvocatorias() {
        modeloConvocatorias = new DefaultTableModel(new String[]{"ID", "Nombre", "Fecha"}, 0);
        tablaConvocatorias = new JTable(modeloConvocatorias);
        modeloConvocatorias.setRowCount(0);

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, nombre, fecha FROM convocatorias")) {
            while (rs.next()) {
                modeloConvocatorias.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha")
                });
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para agregar un jugador a la lista de seleccionados
    private void agregarJugadorASeleccionados() {
        int fila = tablaJugadores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador para agregar.");
            return;
        }

        if (modeloSeleccionados.getRowCount() >= 16) {
            JOptionPane.showMessageDialog(this, "No se pueden seleccionar más de 16 jugadores.");
            return;
        }

        Object[] jugador = new Object[modeloJugadores.getColumnCount()];
        for (int i = 0; i < modeloJugadores.getColumnCount(); i++) {
            jugador[i] = modeloJugadores.getValueAt(fila, i);
        }

        modeloSeleccionados.addRow(jugador);
        modeloJugadores.removeRow(fila);
    }

    // Método para remover un jugador de la lista de seleccionados
    private void removerJugadorDeSeleccionados() {
        int fila = tablaSeleccionados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador para remover.");
            return;
        }

        Object[] jugador = new Object[modeloSeleccionados.getColumnCount()];
        for (int i = 0; i < modeloSeleccionados.getColumnCount(); i++) {
            jugador[i] = modeloSeleccionados.getValueAt(fila, i);
        }

        modeloJugadores.addRow(jugador);
        modeloSeleccionados.removeRow(fila);
    }

    // Método para guardar una convocatoria en la base de datos
    private void guardarConvocatoria() {
        String nombreConvocatoria = tfNombreConvocatoria.getText();
        Date fecha = (Date) spinnerFecha.getValue();

        if (nombreConvocatoria.isEmpty() || modeloSeleccionados.getRowCount() != 16) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para la convocatoria y seleccione 16 jugadores.");
            return;
        }

        try (Connection con = ConexionDB.conectar()) {
            // Insertar la convocatoria
            String sqlConvocatoria = "INSERT INTO convocatorias (nombre, fecha) VALUES (?, ?)";
            PreparedStatement psConvocatoria = con.prepareStatement(sqlConvocatoria, Statement.RETURN_GENERATED_KEYS);
            psConvocatoria.setString(1, nombreConvocatoria);
            psConvocatoria.setDate(2, new java.sql.Date(fecha.getTime()));
            psConvocatoria.executeUpdate();

            // Obtener el ID de la convocatoria recién creada
            ResultSet rsKeys = psConvocatoria.getGeneratedKeys();
            if (!rsKeys.next()) throw new SQLException("Error al obtener el ID de la convocatoria.");
            int convocatoriaId = rsKeys.getInt(1);

            // Insertar los jugadores seleccionados
            String sqlJugadores = "INSERT INTO convocatoria_jugadores (convocatoria_id, jugador_id) VALUES (?, ?)";
            PreparedStatement psJugadores = con.prepareStatement(sqlJugadores);
            for (int i = 0; i < modeloSeleccionados.getRowCount(); i++) {
                int jugadorId = (int) modeloSeleccionados.getValueAt(i, 0);
                psJugadores.setInt(1, convocatoriaId);
                psJugadores.setInt(2, jugadorId);
                psJugadores.addBatch();
            }
            psJugadores.executeBatch();

            JOptionPane.showMessageDialog(this, "Convocatoria guardada correctamente.");
            limpiarCampos();
            cargarJugadores();
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        tfNombreConvocatoria.setText("");
        spinnerFecha.setValue(new Date());
        modeloSeleccionados.setRowCount(0);
    }

    // Método para mostrar errores en un cuadro de diálogo
    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}