package com.example.ef_michaelruiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ef_michaelruiz.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            saveTeam()
        }

        binding.buttonRegisterMatches.setOnClickListener {
            val intent = Intent(this, Registro_Enfrentamientos::class.java)
            startActivity(intent)
        }

        binding.buttonMatchList.setOnClickListener {
        }

    }

        private fun saveTeam() {
            val teamName = binding.editTextTeamName.text.toString()
            val teamLogoUrl = binding.editTextTeamLogoUrl.text.toString()

            if (teamName.isNotEmpty() && teamLogoUrl.isNotEmpty()) {
                val team = hashMapOf(
                    "name" to teamName,
                    "logoUrl" to teamLogoUrl
                )

                db.collection("teams")
                    .add(team)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Equipo guardado exitosamente", Toast.LENGTH_SHORT)
                            .show()
                        binding.editTextTeamName.text.clear()
                        binding.editTextTeamLogoUrl.text.clear()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al guardar el equipo: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }