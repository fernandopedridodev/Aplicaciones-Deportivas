/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scoutapp;

/**
 *
 * @author fernando.pedridomarino
 */
/**
 * Clase que representa una acción técnica que puede ser evaluada.
 */
public class AccionTecnica {
    // Atributos
    private int id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private int valoracion; // Valoración de 0 a 10
    private String observaciones;

    // Constructor completo
    public AccionTecnica(int id, String nombre, String categoria, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.valoracion = 0; // Inicialmente sin valorar
        this.observaciones = ""; // Sin observaciones inicialmente
    }

    // Métodos Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    // Métodos Setters
    public void setValoracion(int valoracion) {
        if (valoracion < 0 || valoracion > 10) {
            throw new IllegalArgumentException("La valoración debe estar entre 0 y 10.");
        }
        this.valoracion = valoracion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Método para mostrar información de la acción
    @Override
    public String toString() {
        return "AccionTecnica{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", valoracion=" + valoracion +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}