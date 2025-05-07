/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package scoutapp;

/**
 *
 * @author fernando.pedridomarino
 */
/**
 * Clase que representa a un jugador.
 */
public class Jugador {
    private int id;
    private String nombre;
    private String posicion;
    private int dorsal;
    private int edad;
    private String equipo;

    // Constructor
    public Jugador(int id, String nombre, String posicion, int dorsal, int edad, String equipo) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.edad = edad;
        this.equipo = equipo;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    // Método para mostrar información del jugador
    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", posicion='" + posicion + '\'' +
                ", dorsal=" + dorsal +
                ", edad=" + edad +
                ", equipo='" + equipo + '\'' +
                '}';
    }
}