package com.scoutapp.manager

import com.scoutapp.model.AccionTecnica

/**
 * Clase para gestionar las acciones técnicas predefinidas.
 */
object AccionTecnicaManager {

    private val accionesTecnicas = mutableListOf<AccionTecnica>()

    init {
        inicializarAccionesTecnicas()
    }

    private fun inicializarAccionesTecnicas() {
        // Acciones técnicas ofensivas
        accionesTecnicas.apply {
            add(AccionTecnica(1, "Control orientado", "Ofensiva", "Control del balón orientado hacia el objetivo."))
            add(AccionTecnica(2, "Control en carrera", "Ofensiva", "Control del balón mientras se corre."))
            add(AccionTecnica(3, "Control bajo presión", "Ofensiva", "Control del balón en situaciones de presión del rival."))

            add(AccionTecnica(4, "Conducción progresiva", "Ofensiva", "Avance con el balón hacia el objetivo."))
            add(AccionTecnica(5, "Conducción lateral o de retención", "Ofensiva", "Movimiento lateral con el balón para retenerlo."))
            add(AccionTecnica(6, "Conducción en transición", "Ofensiva", "Conducción rápida durante la transición."))

            add(AccionTecnica(7, "Pase corto", "Ofensiva", "Pase preciso a corta distancia."))
            add(AccionTecnica(8, "Pase largo", "Ofensiva", "Pase a larga distancia."))
            add(AccionTecnica(9, "Pase entre líneas", "Ofensiva", "Pase que atraviesa líneas defensivas."))
            add(AccionTecnica(10, "Pase en profundidad", "Ofensiva", "Pase hacia adelante para habilitar al atacante."))
            add(AccionTecnica(11, "Pase de ruptura", "Ofensiva", "Pase que rompe la defensa rival."))
            add(AccionTecnica(12, "Paredes (1-2)", "Ofensiva", "Ejecución de pases rápidos 1-2 entre jugadores."))
            add(AccionTecnica(13, "Centros y centros al área", "Ofensiva", "Centros dirigidos a la zona de ataque."))

            add(AccionTecnica(14, "Tiros a puerta", "Ofensiva", "Intentos de marcar gol."))
            add(AccionTecnica(15, "Goles", "Ofensiva", "Goles marcados."))
            add(AccionTecnica(16, "Tiros bloqueados", "Ofensiva", "Tiros bloqueados por defensores."))
            add(AccionTecnica(17, "Tiros desde fuera del área", "Ofensiva", "Intentos de gol desde larga distancia."))
            add(AccionTecnica(18, "Remates de cabeza", "Ofensiva", "Remates realizados con la cabeza."))
            add(AccionTecnica(19, "Remates en el área chica", "Ofensiva", "Remates realizados desde el área chica."))

            add(AccionTecnica(20, "Intentos de regate", "Ofensiva", "Intentos de superar al rival en 1v1."))
            add(AccionTecnica(21, "Regates exitosos", "Ofensiva", "Regates completados con éxito."))
            add(AccionTecnica(22, "Duelos 1vs1 ofensivos ganados", "Ofensiva", "Duelos ofensivos ganados en 1v1."))

            add(AccionTecnica(23, "Recepción al pie", "Ofensiva", "Recepción del balón directamente al pie."))
            add(AccionTecnica(24, "Recepción al espacio", "Ofensiva", "Recepción del balón en un espacio libre."))
            add(AccionTecnica(25, "Recepción bajo presión", "Ofensiva", "Recepción del balón mientras se enfrenta presión del rival."))

            // Acciones técnicas defensivas
            add(AccionTecnica(26, "Entradas exitosas", "Defensiva", "Entradas que recuperan el balón con éxito."))
            add(AccionTecnica(27, "Entradas fallidas", "Defensiva", "Entradas sin éxito que no recuperan el balón."))
            add(AccionTecnica(28, "Duelos 1vs1 defensivos ganados", "Defensiva", "Duelos defensivos ganados en 1v1."))
            add(AccionTecnica(29, "Duelos aéreos ganados", "Defensiva", "Duelos aéreos ganados."))
            add(AccionTecnica(30, "Duelos perdidos", "Defensiva", "Duelos perdidos en situaciones defensivas."))
            add(AccionTecnica(31, "Interceptaciones de pase", "Defensiva", "Interceptaciones exitosas de pases."))
            add(AccionTecnica(32, "Despejes en área propia", "Defensiva", "Despejes realizados en el área propia."))
            add(AccionTecnica(33, "Despejes tras centro", "Defensiva", "Despejes realizados tras centros al área."))
            add(AccionTecnica(34, "Acciones de presión alta, media o baja", "Defensiva", "Presión defensiva en diferentes zonas."))
            add(AccionTecnica(35, "Presiones exitosas", "Defensiva", "Acciones de presión que resultaron en recuperación del balón."))
            add(AccionTecnica(36, "Coberturas a compañeros", "Defensiva", "Acciones defensivas para cubrir a un compañero."))
            add(AccionTecnica(37, "Ayudas defensivas", "Defensiva", "Acciones de apoyo defensivo."))

            // Acciones técnicas del portero
            add(AccionTecnica(38, "Paradas (intervenciones)", "Portero", "Intervenciones exitosas del portero."))
            add(AccionTecnica(39, "Salidas por alto", "Portero", "Salidas del portero para interceptar balones altos."))
            add(AccionTecnica(40, "Salidas en 1vs1", "Portero", "Intervenciones del portero en 1v1."))
            add(AccionTecnica(41, "Blocajes o rechaces", "Portero", "Blocajes o rechazos del balón."))
            add(AccionTecnica(42, "Juego con los pies (pases cortos/largos)", "Portero", "Pases realizados por el portero."))
            add(AccionTecnica(43, "Recolocaciones tras despeje", "Portero", "Recolocación del portero tras un despeje."))
            add(AccionTecnica(44, "Penaltis detenidos", "Portero", "Penaltis atajados por el portero."))

            // Errores técnicos
            add(AccionTecnica(45, "Pérdidas de balón no forzadas", "Errores", "Errores en la posesión sin presión del rival."))
            add(AccionTecnica(46, "Malos controles", "Errores", "Errores en el control del balón."))
            add(AccionTecnica(47, "Fallos en el pase", "Errores", "Errores en la ejecución de pases."))
            add(AccionTecnica(48, "Fallos en la finalización", "Errores", "Errores en intentos de gol."))
            add(AccionTecnica(49, "Malas decisiones en salida de balón", "Errores", "Decisiones erróneas al iniciar una jugada."))
            add(AccionTecnica(50, "Fallos de marcaje", "Errores", "Errores en el marcaje defensivo."))

            // Acciones especiales
            add(AccionTecnica(51, "Saques de esquina lanzados/recibidos", "Especiales", "Acciones en saques de esquina."))
            add(AccionTecnica(52, "Faltas directas/indirectas", "Especiales", "Lanzamiento de faltas directas o indirectas."))
            add(AccionTecnica(53, "Penaltis", "Especiales", "Ejecución de penaltis."))
            add(AccionTecnica(54, "Cambios de orientación", "Especiales", "Pases largos para cambiar el punto de ataque."))
            add(AccionTecnica(55, "Pases de seguridad", "Especiales", "Pases realizados para mantener la posesión."))
            add(AccionTecnica(56, "Retención de balón en situaciones de ventaja", "Especiales", "Mantener el balón para asegurar la ventaja."))
        }
    }

    fun getAccionesTecnicas(): List<AccionTecnica> = accionesTecnicas

    fun getAccionesPorCategoria(categoria: String): List<AccionTecnica> =
        accionesTecnicas.filter { it.categoria == categoria }
}
