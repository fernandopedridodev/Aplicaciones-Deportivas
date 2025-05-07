package futapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PanelJugadores extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField tfNombre, tfPosicion, tfDorsal, tfEdad;

    public PanelJugadores() {
        setLayout(new BorderLayout());

        // Tabla de jugadores
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición", "Dorsal", "Edad"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar/editar jugadores
        JPanel panelInferior = new JPanel(new GridLayout(2, 5));
        tfNombre = new JTextField();
        tfPosicion = new JTextField();
        tfDorsal = new JTextField();
        tfEdad = new JTextField();

        panelInferior.add(new JLabel("Nombre:"));
        panelInferior.add(new JLabel("Posición:"));
        panelInferior.add(new JLabel("Dorsal:"));
        panelInferior.add(new JLabel("Edad:"));
        panelInferior.add(new JLabel(""));

        panelInferior.add(tfNombre);
        panelInferior.add(tfPosicion);
        panelInferior.add(tfDorsal);
        panelInferior.add(tfEdad);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelInferior.add(btnAgregar);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnLimpiar);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarDatos();

        // Eventos de botones
        btnAgregar.addActionListener(e -> agregarJugador());
        btnActualizar.addActionListener(e -> actualizarJugador());
        btnEliminar.addActionListener(e -> eliminarJugador());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Evento para seleccionar datos de la tabla
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    tfNombre.setText(modelo.getValueAt(fila, 1).toString());
                    tfPosicion.setText(modelo.getValueAt(fila, 2).toString());
                    tfDorsal.setText(modelo.getValueAt(fila, 3).toString());
                    tfEdad.setText(modelo.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    // Método para cargar datos desde la base de datos
    private void cargarDatos() {
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar datos
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM jugadores")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        rs.getInt("edad")
                });
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para agregar un nuevo jugador
    private void agregarJugador() {
        if (!validarCampos()) return;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement("INSERT INTO jugadores (nombre, posicion, dorsal, edad) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, tfNombre.getText());
            ps.setString(2, tfPosicion.getText());
            ps.setInt(3, Integer.parseInt(tfDorsal.getText()));
            ps.setInt(4, Integer.parseInt(tfEdad.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Jugador agregado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para actualizar un jugador existente
    private void actualizarJugador() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador para actualizar.");
            return;
        }
        if (!validarCampos()) return;

        int id = (int) modelo.getValueAt(fila, 0);
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement("UPDATE jugadores SET nombre=?, posicion=?, dorsal=?, edad=? WHERE id=?")) {
            ps.setString(1, tfNombre.getText());
            ps.setString(2, tfPosicion.getText());
            ps.setInt(3, Integer.parseInt(tfDorsal.getText()));
            ps.setInt(4, Integer.parseInt(tfEdad.getText()));
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Jugador actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para eliminar un jugador
    private void eliminarJugador() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar jugador?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement("DELETE FROM jugadores WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Jugador eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Validar que los campos del formulario estén completos y sean válidos
    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfPosicion.getText().isEmpty() || tfDorsal.getText().isEmpty() || tfEdad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return false;
        }
        try {
            Integer.parseInt(tfDorsal.getText());
            Integer.parseInt(tfEdad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dorsal y Edad deben ser números válidos.");
            return false;
        }
        return true;
    }

    // Limpiar los campos del formulario
    private void limpiarCampos() {
        tfNombre.setText("");
        tfPosicion.setText("");
        tfDorsal.setText("");
        tfEdad.setText("");
        tabla.clearSelection();
    }

    // Mostrar errores en un cuadro de diálogo
    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}