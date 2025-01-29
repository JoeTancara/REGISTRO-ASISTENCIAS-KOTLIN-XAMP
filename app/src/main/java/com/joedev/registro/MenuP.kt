package com.joedev.registro

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joedev.registro.databinding.ActivityMenuPBinding

class MenuP : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.materiaButton.setOnClickListener {
            val intent = Intent(this, Materias::class.java)
            startActivity(intent)
        }

        binding.estudiantesButton.setOnClickListener {
            val intent = Intent(this, Estudiantes::class.java)
            startActivity(intent)
        }

        binding.asistenciasButton.setOnClickListener {
            val intent = Intent(this, Asistencias::class.java)
            startActivity(intent)
        }
    }
}