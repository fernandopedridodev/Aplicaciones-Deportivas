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

public class PanelPlanPartido extends JPanel {
    private JComboBox<String> comboConvocatorias;
    private JComboBox<String> comboEsquema;
    private JTable tablaJugadoresConvocados;
    private DefaultTableModel modeloJugadoresConvocados;
    private JTable tablaTitulares;
    private DefaultTableModel modeloTitulares;
    private JTable tablaSuplentes;
    private DefaultTableModel modeloSuplentes;
    private JButton btnAgregarTitular, btnAgregarSuplente, btnRemoverTitular, btnRemoverSuplente, btnGuardarPlan;

    public PanelPlanPartido() {
        setLayout(new BorderLayout());

        // Panel superior: Selección de convocatoria y esquema táctico
        JPanel panelSuperior = new JPanel(new GridLayout(2, 3));
        comboConvocatorias = new JComboBox<>();
        comboEsquema = new JComboBox<>(new String[]{"4-4-2", "4-3-3", "3-5-2", "4-2-3-1", "5-3-2"});

        panelSuperior.add(new JLabel("Convocatoria:"));
        panelSuperior.add(new JLabel("Esquema Táctico:"));
        panelSuperior.add(new JLabel(""));
        panelSuperior.add(comboConvocatorias);
        panelSuperior.add(comboEsquema);
        panelSuperior.add(new JLabel(""));

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: tablas de jugadores convocados, titulares y suplentes
        JPanel panelCentral = new JPanel(new GridLayout(1, 3));

        // Tabla de jugadores convocados
        modeloJugadoresConvocados = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición"}, 0);
        tablaJugadoresConvocados = new JTable(modeloJugadoresConvocados);
        JScrollPane scrollConvocados = new JScrollPane(tablaJugadoresConvocados);
        panelCentral.add(scrollConvocados);

        // Tabla de titulares
        modeloTitulares = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición"}, 0);
        tablaTitulares = new JTable(modeloTitulares);
        JScrollPane scrollTitulares = new JScrollPane(tablaTitulares);
        panelCentral.add(scrollTitulares);

        // Tabla de suplentes
        modeloSuplentes = new DefaultTableModel(new String[]{"ID", "Nombre", "Posición"}, 0);
        tablaSuplentes = new JTable(modeloSuplentes);
        JScrollPane scrollSuplentes = new JScrollPane(tablaSuplentes);
        panelCentral.add(scrollSuplentes);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior: botones de acción
        JPanel panelInferior = new JPanel(new GridLayout(2, 2));
        btnAgregarTitular = new JButton("Agregar a Titulares");
        btnAgregarSuplente = new JButton("Agregar a Suplentes");
        btnRemoverTitular = new JButton("Remover de Titulares");
        btnRemoverSuplente = new JButton("Remover de Suplentes");
        btnGuardarPlan = new JButton("Guardar Plan de Partido");

        panelInferior.add(btnAgregarTitular);
        panelInferior.add(btnAgregarSuplente);
        panelInferior.add(btnRemoverTitular);
        panelInferior.add(btnRemoverSuplente);

        add(panelInferior, BorderLayout.SOUTH);
        add(btnGuardarPlan, BorderLayout.PAGE_END);

        // Cargar convocatorias al iniciar
        cargarConvocatorias();

        // Eventos
        comboConvocatorias.addActionListener(e -> cargarJugadoresDeConvocatoria());
        btnAgregarTitular.addActionListener(e -> agregarATitulares());
        btnAgregarSuplente.addActionListener(e -> agregarASuplentes());
        btnRemoverTitular.addActionListener(e -> removerDeTitulares());
        btnRemoverSuplente.addActionListener(e -> removerDeSuplentes());
        btnGuardarPlan.addActionListener(e -> guardarPlanDePartido());
    }

    // Método para cargar las convocatorias existentes en el combo
    private void cargarConvocatorias() {
        comboConvocatorias.removeAllItems();
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, nombre FROM convocatorias")) {
            while (rs.next()) {
                comboConvocatorias.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para cargar jugadores de la convocatoria seleccionada
    private void cargarJugadoresDeConvocatoria() {
        modeloJugadoresConvocados.setRowCount(0);
        String seleccion = (String) comboConvocatorias.getSelectedItem();
        if (seleccion == null) return;

        int convocatoriaId = Integer.parseInt(seleccion.split(" - ")[0]);

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT jugadores.id, jugadores.nombre, jugadores.posicion " +
                             "FROM jugadores " +
                             "JOIN convocatoria_jugadores ON jugadores.id = convocatoria_jugadores.jugador_id " +
                             "WHERE convocatoria_jugadores.convocatoria_id = ?")) {
            ps.setInt(1, convocatoriaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modeloJugadoresConvocados.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("posicion")
                });
            }
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para mover jugadores a titulares
    private void agregarATitulares() {
        if (modeloTitulares.getRowCount() >= 11) {
            JOptionPane.showMessageDialog(this, "Solo se pueden asignar 11 jugadores como titulares.");
            return;
        }
        moverJugadorEntreTablas(tablaJugadoresConvocados, modeloJugadoresConvocados, modeloTitulares);
    }

    // Método para mover jugadores a suplentes
    private void agregarASuplentes() {
        if (modeloSuplentes.getRowCount() >= 5) {
            JOptionPane.showMessageDialog(this, "Solo se pueden asignar 5 jugadores como suplentes.");
            return;
        }
        moverJugadorEntreTablas(tablaJugadoresConvocados, modeloJugadoresConvocados, modeloSuplentes);
    }

    // Método para remover jugadores de titulares
    private void removerDeTitulares() {
        moverJugadorEntreTablas(tablaTitulares, modeloTitulares, modeloJugadoresConvocados);
    }

    // Método para remover jugadores de suplentes
    private void removerDeSuplentes() {
        moverJugadorEntreTablas(tablaSuplentes, modeloSuplentes, modeloJugadoresConvocados);
    }

    // Método genérico para mover jugadores entre tablas
    private void moverJugadorEntreTablas(JTable origen, DefaultTableModel modeloOrigen, DefaultTableModel modeloDestino) {
        int fila = origen.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador.");
            return;
        }

        Object[] jugador = new Object[modeloOrigen.getColumnCount()];
        for (int i = 0; i < modeloOrigen.getColumnCount(); i++) {
            jugador[i] = modeloOrigen.getValueAt(fila, i);
        }

        modeloDestino.addRow(jugador);
        modeloOrigen.removeRow(fila);
    }

    // Método para guardar el plan de partido
    private void guardarPlanDePartido() {
        String seleccion = (String) comboConvocatorias.getSelectedItem();
        if (seleccion == null || modeloTitulares.getRowCount() != 11 || modeloSuplentes.getRowCount() != 5) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una convocatoria y asignar 11 titulares y 5 suplentes.");
            return;
        }

        int convocatoriaId = Integer.parseInt(seleccion.split(" - ")[0]);
        String esquema = (String) comboEsquema.getSelectedItem();

        try (Connection con = ConexionDB.conectar()) {
            // Guardar esquema táctico
            String sqlEsquema = "UPDATE convocatorias SET esquema = ? WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlEsquema)) {
                ps.setString(1, esquema);
                ps.setInt(2, convocatoriaId);
                ps.executeUpdate();
            }

            // Guardar titulares
            String sqlTitulares = "UPDATE convocatoria_jugadores SET es_titular = TRUE, es_suplente = FALSE WHERE convocatoria_id = ? AND jugador_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlTitulares)) {
                for (int i = 0; i < modeloTitulares.getRowCount(); i++) {
                    ps.setInt(1, convocatoriaId);
                    ps.setInt(2, (int) modeloTitulares.getValueAt(i, 0));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Guardar suplentes
            String sqlSuplentes = "UPDATE convocatoria_jugadores SET es_titular = FALSE, es_suplente = TRUE WHERE convocatoria_id = ? AND jugador_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlSuplentes)) {
                for (int i = 0; i < modeloSuplentes.getRowCount(); i++) {
                    ps.setInt(1, convocatoriaId);
                    ps.setInt(2, (int) modeloSuplentes.getValueAt(i, 0));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            JOptionPane.showMessageDialog(this, "Plan de partido guardado correctamente.");
        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Método para mostrar errores
    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}