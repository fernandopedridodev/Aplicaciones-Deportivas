package com.scoutapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.scoutapp.R
import com.scoutapp.model.Jugador

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNombre = findViewById<TextInputEditText>(R.id.et_player_name)
        val etPosicion = findViewById<TextInputEditText>(R.id.et_player_position)
        val etEdad = findViewById<TextInputEditText>(R.id.et_player_age)
        val etDorsal = findViewById<TextInputEditText>(R.id.et_player_number)
        val etEquipo = findViewById<TextInputEditText>(R.id.et_player_team)
        val btnContinuar = findViewById<MaterialButton>(R.id.btn_continue)
        val btnCancelar = findViewById<MaterialButton>(R.id.btn_cancel)

        btnContinuar.setOnClickListener {
            if (validarCampos(etNombre, etPosicion, etEdad, etDorsal, etEquipo)) {
                val nombre = etNombre.text.toString()
                val posicion = etPosicion.text.toString()
                val edad = etEdad.text.toString().toInt()
                val dorsal = etDorsal.text.toString().toInt()
                val equipo = etEquipo.text.toString()

                val jugador = Jugador(1, nombre, posicion, dorsal, edad, equipo)

                val intent = Intent(this, EvaluacionActivity::class.java)
                intent.putExtra("jugador_nombre", jugador.nombre)
                intent.putExtra("jugador_posicion", jugador.posicion)
                intent.putExtra("jugador_dorsal", jugador.dorsal)
                intent.putExtra("jugador_edad", jugador.edad)
                intent.putExtra("jugador_equipo", jugador.equipo)
                startActivity(intent)
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun validarCampos(
        etNombre: TextInputEditText,
        etPosicion: TextInputEditText,
        etEdad: TextInputEditText,
        etDorsal: TextInputEditText,
        etEquipo: TextInputEditText
    ): Boolean {
        return when {
            etNombre.text.toString().isBlank() -> {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
                false
            }
            etPosicion.text.toString().isBlank() -> {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
                false
            }
            etEdad.text.toString().isBlank() -> {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
                false
            }
            etEdad.text.toString().toIntOrNull() == null || etEdad.text.toString().toInt() <= 0 -> {
                Toast.makeText(this, R.string.error_invalid_age, Toast.LENGTH_SHORT).show()
                false
            }
            etDorsal.text.toString().isBlank() -> {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
                false
            }
            etDorsal.text.toString().toIntOrNull() == null || etDorsal.text.toString().toInt() <= 0 -> {
                Toast.makeText(this, R.string.error_invalid_number, Toast.LENGTH_SHORT).show()
                false
            }
            etEquipo.text.toString().isBlank() -> {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
