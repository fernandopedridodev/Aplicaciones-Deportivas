package com.scoutapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.scoutapp.R

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val btnEvaluacion = findViewById<MaterialButton>(R.id.btn_evaluation)
        val btnSalir = findViewById<MaterialButton>(R.id.btn_exit)

        btnEvaluacion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }
}
