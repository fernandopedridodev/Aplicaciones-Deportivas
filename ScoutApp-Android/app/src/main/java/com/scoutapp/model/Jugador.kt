package com.scoutapp.model

/**
 * Clase que representa a un jugador.
 */
data class Jugador(
    val id: Int,
    var nombre: String,
    var posicion: String,
    var dorsal: Int,
    var edad: Int,
    var equipo: String
)
