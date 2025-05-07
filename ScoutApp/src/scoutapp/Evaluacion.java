/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scoutapp;

/**
 *
 * @author fernando.pedridomarino
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa la evaluación de un jugador con interfaz gráfica.
 */
public class Evaluacion extends JFrame {
    private Jugador jugador; // El jugador evaluado
    private Map<AccionTecnica, Integer> valoraciones; // Mapa de acciones técnicas y sus valoraciones
    private String observacionesGenerales; // Observaciones generales sobre el jugador
    private JTable accionesTable; // Tabla de acciones técnicas
    private JTextArea observacionesArea; // Área de texto para observaciones generales

    // Constructor para inicializar la evaluación
    public Evaluacion(Jugador jugador) {
        this.jugador = jugador;
        this.valoraciones = new HashMap<>();
        this.observacionesGenerales = "";

        // Configuración de la ventana
        setTitle("Evaluación de Jugador: " + jugador.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear y configurar la tabla de acciones técnicas
        String[] columnNames = {"Acción Técnica", "Valoración"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        accionesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(accionesTable);

        // Agregar las acciones técnicas a la tabla
        List<AccionTecnica> acciones = AccionTecnicaManager.getAccionesTecnicas();
        for (AccionTecnica accion : acciones) {
            tableModel.addRow(new Object[]{accion.getNombre(), 0}); // Valoración inicial: 0
            valoraciones.put(accion, 0); // Registrar en el mapa
        }

        // Área de observaciones generales
        JPanel observacionesPanel = new JPanel(new BorderLayout());
        observacionesPanel.setBorder(BorderFactory.createTitledBorder("Observaciones Generales"));
        observacionesArea = new JTextArea(5, 20);
        observacionesPanel.add(new JScrollPane(observacionesArea), BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton guardarButton = new JButton("Guardar Evaluación");
        JButton limpiarButton = new JButton("Limpiar");
        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);

        // Acción del botón "Guardar Evaluación"
        guardarButton.addActionListener(e -> guardarEvaluacion());

        // Acción del botón "Limpiar"
        limpiarButton.addActionListener(e -> limpiarEvaluacion());

        // Agregar componentes a la ventana
        add(tableScrollPane, BorderLayout.CENTER);
        add(observacionesPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para guardar la evaluación
    private void guardarEvaluacion() {
        DefaultTableModel tableModel = (DefaultTableModel) accionesTable.getModel();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String nombreAccion = (String) tableModel.getValueAt(i, 0);
            try {
                int valoracion = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                if (valoracion < 0 || valoracion > 10) {
                    throw new NumberFormatException();
                }

                // Buscar la acción técnica correspondiente
                for (AccionTecnica accion : valoraciones.keySet()) {
                    if (accion.getNombre().equals(nombreAccion)) {
                        valoraciones.put(accion, valoracion);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valoración inválida para la acción: " + nombreAccion + ". Debe estar entre 0 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Guardar las observaciones generales
        observacionesGenerales = observacionesArea.getText();
        JOptionPane.showMessageDialog(this, "Evaluación guardada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para limpiar la evaluación
    private void limpiarEvaluacion() {
        DefaultTableModel tableModel = (DefaultTableModel) accionesTable.getModel();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(0, i, 1); // Reiniciar las valoraciones a 0
        }
        observacionesArea.setText(""); // Limpiar observaciones
        JOptionPane.showMessageDialog(this, "Evaluación limpiada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método principal para probar la clase
    public static void main(String[] args) {
        // Crear un jugador de ejemplo
        Jugador jugador = new Jugador(1, "Lionel Messi", "Delantero", 10, 35, "Inter Miami");

        // Crear y mostrar la evaluación
        new Evaluacion(jugador);
    }
}