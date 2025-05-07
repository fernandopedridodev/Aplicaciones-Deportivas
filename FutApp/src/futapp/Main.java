/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futapp;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author fernando.pedridomarino
 */
public class Main {
    public static void main(String[] args) {
        // Ejecutar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // Crear el marco principal de la aplicación
            JFrame frame = new JFrame("Gestión de Equipo de Fútbol");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);

            // Crear un contenedor de pestañas
            JTabbedPane tabbedPane = new JTabbedPane();

            // Agregar las diferentes pestañas al contenedor
            tabbedPane.addTab("Jugadores", new PanelJugadores());
            tabbedPane.addTab("Multas", new PanelMultas());
            tabbedPane.addTab("Convocatorias", new PanelConvocatorias());
            tabbedPane.addTab("Plan de Partido", new PanelPlanPartido());

            // Agregar el contenedor de pestañas al marco principal
            frame.add(tabbedPane);

            // Hacer visible el marco principal
            frame.setVisible(true);
        });
    }
}