package com.scoutapp.model

/**
 * Clase que representa una acción técnica que puede ser evaluada.
 */
data class AccionTecnica(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val descripcion: String,
    var valoracion: Int = 0,
    var observaciones: String = ""
) {
    init {
        require(valoracion in 0..10) { "La valoración debe estar entre 0 y 10." }
    }

    fun setValoracion(valor: Int) {
        require(valor in 0..10) { "La valoración debe estar entre 0 y 10." }
    }
}
