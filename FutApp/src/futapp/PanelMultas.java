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

public class PanelMultas extends JPanel {
    private JTable tablaMultas;
    private DefaultTableModel modeloMultas;
    private JComboBox<String> comboJugadores;
    private JTextField tfMotivo, tfCantidad;

    public PanelMultas() {
        setLayout(new BorderLayout());

        // Tabla de multas
        modeloMultas = new DefaultTableModel(new String[]{"ID", "Jugador", "Motivo", "Cantidad (€)"}, 0);
        tablaMultas = new JTable(modeloMultas);
        JScrollPane scrollPane = new JScrollPane(tablaMultas);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar multas
        JPanel panelInferior = new JPanel(new GridLayout(2, 4));
        comboJugadores = new JComboBox<>();
        tfMotivo = new JTextField();
        tfCantidad = new JTextField();

        panelInferior.add(new JLabel("Jugador:"));
        panelInferior.add(new JLabel("Motivo:"));
        panelInferior.add(new JLabel("Cantidad (€):"));
        panelInferior.add(new JLabel(""));

        panelInferior.add(comboJugadores);
        panelInferior.add(tfMotivo);
        panelInferior.add(tfCantidad);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelInferior.add(btnAgregar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnLimpiar);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarJugadores();
        cargarMultas();

        // Eventos de botones
        btnAgregar.addActionListener(e -> agregarMulta());
        btnEliminar.addActionListener(e -> eliminarMulta());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    // Método para cargar jugadores en el combo
    private void cargarJugadores() {
        comboJugadores.removeAllItems();
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, nombre FROM jugadores")) {
            while (rs.next()) {
                comboJugadores.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para cargar multas en la tabla
    private void cargarMultas() {
        modeloMultas.setRowCount(0);
        String query = "SELECT multas.id, jugadores.nombre, multas.motivo, multas.cantidad " +
                       "FROM multas JOIN jugadores ON multas.jugador_id = jugadores.id";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                modeloMultas.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("motivo"),
                        rs.getDouble("cantidad")
                });
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para agregar una nueva multa
    private void agregarMulta() {
        String jugadorSeleccionado = (String) comboJugadores.getSelectedItem();
        if (jugadorSeleccionado == null || tfMotivo.getText().isEmpty() || tfCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            int jugadorId = Integer.parseInt(jugadorSeleccionado.split(" - ")[0]);
            String motivo = tfMotivo.getText();
            double cantidad = Double.parseDouble(tfCantidad.getText());

            try (Connection con = ConexionDB.conectar();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO multas (jugador_id, motivo, cantidad) VALUES (?, ?, ?)")) {
                ps.setInt(1, jugadorId);
                ps.setString(2, motivo);
                ps.setDouble(3, cantidad);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Multa agregada correctamente.");
                limpiarCampos();
                cargarMultas();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.");
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para eliminar una multa seleccionada
    private void eliminarMulta() {
        int fila = tablaMultas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una multa para eliminar.");
            return;
        }

        int multaId = (int) modeloMultas.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar multa?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement("DELETE FROM multas WHERE id = ?")) {
            ps.setInt(1, multaId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Multa eliminada correctamente.");
            cargarMultas();
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        comboJugadores.setSelectedIndex(-1);
        tfMotivo.setText("");
        tfCantidad.setText("");
        tablaMultas.clearSelection();
    }

    // Método para mostrar errores en un cuadro de diálogo
    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}