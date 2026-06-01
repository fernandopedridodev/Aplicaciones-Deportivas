package com.scoutapp.util

import android.content.Context
import android.os.Environment
import com.scoutapp.model.AccionTecnica
import com.tom_roush.pdfbox.pdfdocument.PDFDocument
import com.tom_roush.pdfbox.pdfpage.PDFPage
import com.tom_roush.pdfbox.pdfwriter.PDFWriter
import com.tom_roush.pdfbox.util.PDFBoxUtils
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PDFExporter(private val context: Context) {

    fun exportarEvaluacion(
        nombreJugador: String,
        posicion: String,
        dorsal: Int,
        edad: Int,
        equipo: String,
        acciones: List<AccionTecnica>,
        observaciones: String
    ) {
        try {
            PDFBoxUtils.init(context)

            val document = PDFDocument()
            val page = PDFPage()
            document.addPage(page)

            val pdfPath = generarRutaArchivo(nombreJugador)
            val outputStream = FileOutputStream(pdfPath)

            // Aquí irían las operaciones de escritura del PDF
            // usando PDFBox. Por ahora, creamos un archivo básico.
            val contenido = generarContenidoPDF(
                nombreJugador,
                posicion,
                dorsal,
                edad,
                equipo,
                acciones,
                observaciones
            )

            outputStream.write(contenido.toByteArray())
            outputStream.close()

        } catch (e: Exception) {
            throw Exception("Error al exportar PDF: ${e.message}")
        }
    }

    private fun generarRutaArchivo(nombreJugador: String): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val nombreArchivo = "Evaluacion_${nombreJugador}_$timestamp.pdf"

        val directorio = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "ScoutApp"
        )

        if (!directorio.exists()) {
            directorio.mkdirs()
        }

        return File(directorio, nombreArchivo).absolutePath
    }

    private fun generarContenidoPDF(
        nombreJugador: String,
        posicion: String,
        dorsal: Int,
        edad: Int,
        equipo: String,
        acciones: List<AccionTecnica>,
        observaciones: String
    ): String {
        val sb = StringBuilder()

        sb.append("======================================\n")
        sb.append("EVALUACIÓN DE JUGADOR - ScoutApp\n")
        sb.append("======================================\n\n")

        sb.append("DATOS DEL JUGADOR:\n")
        sb.append("Nombre: $nombreJugador\n")
        sb.append("Posición: $posicion\n")
        sb.append("Dorsal: $dorsal\n")
        sb.append("Edad: $edad años\n")
        sb.append("Equipo: $equipo\n\n")

        sb.append("EVALUACIÓN DE ACCIONES TÉCNICAS:\n")
        sb.append("======================================\n\n")

        var categoriaActual = ""
        acciones.filter { it.valoracion > 0 }.forEach { accion ->
            if (accion.categoria != categoriaActual) {
                sb.append("\n${accion.categoria.toUpperCase()}\n")
                sb.append("---\n")
                categoriaActual = accion.categoria
            }

            sb.append("• ${accion.nombre}\n")
            sb.append("  Valoración: ${accion.valoracion}/10\n")
            if (accion.observaciones.isNotBlank()) {
                sb.append("  Observaciones: ${accion.observaciones}\n")
            }
        }

        sb.append("\n\nOBSERVACIONES GENERALES:\n")
        sb.append("======================================\n")
        sb.append(observaciones.ifBlank { "Sin observaciones" })
        sb.append("\n\n")

        sb.append("Fecha de evaluación: ")
        sb.append(SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date()))

        return sb.toString()
    }
}
