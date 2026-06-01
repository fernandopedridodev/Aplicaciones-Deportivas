package com.scoutapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.scoutapp.R
import com.scoutapp.manager.AccionTecnicaManager
import com.scoutapp.ui.adapter.AccionTecnicaAdapter
import com.scoutapp.util.PDFExporter

class EvaluacionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccionTecnicaAdapter
    private lateinit var etObservaciones: TextInputEditText

    private var nombreJugador = ""
    private var posicionJugador = ""
    private var dorsalJugador = 0
    private var edadJugador = 0
    private var equipoJugador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluacion)

        // Obtener datos del jugador
        nombreJugador = intent.getStringExtra("jugador_nombre") ?: ""
        posicionJugador = intent.getStringExtra("jugador_posicion") ?: ""
        dorsalJugador = intent.getIntExtra("jugador_dorsal", 0)
        edadJugador = intent.getIntExtra("jugador_edad", 0)
        equipoJugador = intent.getStringExtra("jugador_equipo") ?: ""

        // Configurar el título
        title = getString(R.string.title_evaluation) + ": $nombreJugador"

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recycler_acciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val acciones = AccionTecnicaManager.getAccionesTecnicas()
        adapter = AccionTecnicaAdapter(acciones)
        recyclerView.adapter = adapter

        // Configurar TextInputEditText para observaciones
        etObservaciones = findViewById(R.id.et_observations)

        // Configurar botones
        val btnGuardar = findViewById<MaterialButton>(R.id.btn_save)
        val btnLimpiar = findViewById<MaterialButton>(R.id.btn_clear)
        val btnExportarPDF = findViewById<MaterialButton>(R.id.btn_export_pdf)

        btnGuardar.setOnClickListener { guardarEvaluacion() }
        btnLimpiar.setOnClickListener { limpiarEvaluacion() }
        btnExportarPDF.setOnClickListener { exportarEvaluacionPDF() }
    }

    private fun guardarEvaluacion() {
        Toast.makeText(this, R.string.success_saved, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarEvaluacion() {
        adapter.limpiarValoraciones()
        etObservaciones.text?.clear()
        Toast.makeText(this, R.string.success_cleared, Toast.LENGTH_SHORT).show()
    }

    private fun exportarEvaluacionPDF() {
        try {
            val acciones = adapter.getAcciones()
            val observaciones = etObservaciones.text.toString()

            val exporter = PDFExporter(this)
            exporter.exportarEvaluacion(
                nombreJugador,
                posicionJugador,
                dorsalJugador,
                edadJugador,
                equipoJugador,
                acciones,
                observaciones
            )

            Toast.makeText(this, R.string.success_exported, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "${getString(R.string.error_export)}: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
