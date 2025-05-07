/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scoutapp;

/**
 *
 * @author pedri
 */


import javax.swing.*;


public class Launcher {
    public static void main(String[] args) {
        // Opciones para el usuario
        String[] options = {"Ejecutar Evaluación", "Salir"};

        // Mostrar cuadro de diálogo con las opciones
        int choice = JOptionPane.showOptionDialog(
                null,
                "Selecciona la clase principal a ejecutar:",
                "Launcher",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Procesar la elección del usuario
        switch (choice) {
            case 0: // Ejecutar Main
                JOptionPane.showMessageDialog(null, "Ejecutando Main...");
                scoutapp.Main.main(args); // Llama al método main de la clase Main
                break;
            case 1: // Ejecutar EvaluacionPDF
                JOptionPane.showMessageDialog(null, "Ejecutando EvaluacionPDF...");
                scoutapp.EvaluacionPDF.main(args); // Llama al método main de la clase EvaluacionPDF
                break;
            default: // Salir
                JOptionPane.showMessageDialog(null, "Cerrando la aplicación. ¡Hasta luego!");
                System.exit(0);
        }
    }
}