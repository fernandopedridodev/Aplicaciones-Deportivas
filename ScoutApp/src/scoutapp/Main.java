package scoutapp;

import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fernando.pedridomarino
 */
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Solicitar datos del jugador al usuario
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del jugador es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String posicion = JOptionPane.showInputDialog("Ingrese la posición del jugador:");
        if (posicion == null || posicion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La posición del jugador es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String edadStr = JOptionPane.showInputDialog("Ingrese la edad del jugador:");
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La edad ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dorsalStr = JOptionPane.showInputDialog("Ingrese el dorsal del jugador:");
        int dorsal;
        try {
            dorsal = Integer.parseInt(dorsalStr);
            if (dorsal <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El dorsal ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String equipo = JOptionPane.showInputDialog("Ingrese el equipo del jugador:");
        if (equipo == null || equipo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del equipo es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto Jugador
        Jugador jugador = new Jugador(1, nombre, posicion, dorsal, edad, equipo);

        // Crear y mostrar la evaluación
        new Evaluacion(jugador);
    }
}