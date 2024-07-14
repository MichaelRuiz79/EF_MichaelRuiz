package com.example.ef_michaelruiz

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ef_michaelruiz.databinding.ActivityRegistroEnfrentamientosBinding
import com.google.firebase.firestore.FirebaseFirestore

class Registro_Enfrentamientos : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroEnfrentamientosBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEnfrentamientosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teams = listOf("Team A", "Team B", "Team C")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, teams)

        binding.spinnerLocalTeam.adapter = adapter
        binding.spinnerVisitorTeam.adapter = adapter

        binding.buttonRegisterMatch.setOnClickListener {
            registrarpartidos()
        }
    }

    private fun registrarpartidos() {
        val localTeam = binding.spinnerLocalTeam.selectedItem.toString()
        val visitorTeam = binding.spinnerVisitorTeam.selectedItem.toString()
        val quotaLocal = binding.editTextQuotaLocal.text.toString()
        val quotaDraw = binding.editTextQuotaDraw.text.toString()
        val quotaVisitor = binding.editTextQuotaVisitor.text.toString()

        if (localTeam.isNotEmpty() && visitorTeam.isNotEmpty() && quotaLocal.isNotEmpty() && quotaDraw.isNotEmpty() && quotaVisitor.isNotEmpty()) {
            val match = hashMapOf(
                "Eqlocal" to localTeam,
                "EqVistante" to visitorTeam,
                "CuotaLocal" to quotaLocal,
                "CuotaEmpate" to quotaDraw,
                "CuotaVisitante" to quotaVisitor
            )

            db.collection("partidos")
                .add(match)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Enfrentamiento registrado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.editTextQuotaLocal.text.clear()
                    binding.editTextQuotaDraw.text.clear()
                    binding.editTextQuotaVisitor.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al registrar el enfrentamiento: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}