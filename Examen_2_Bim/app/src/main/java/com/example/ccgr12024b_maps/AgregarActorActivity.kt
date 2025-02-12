package com.example.ccgr12024b_maps

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class AgregarActorActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actor)

        databaseHelper = DatabaseHelper(this)

        val etNombre: EditText = findViewById(R.id.etNombre)
        val etNacionalidad: EditText = findViewById(R.id.etNacionalidad)
        val etEdad: EditText = findViewById(R.id.etEdad)
        val etAltura: EditText = findViewById(R.id.etAltura)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val etLatitud: EditText = findViewById(R.id.etLatitud)
        val etLongitud: EditText = findViewById(R.id.etLongitud)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val nacionalidad = etNacionalidad.text.toString()
            val edad = etEdad.text.toString().toIntOrNull() ?: 0
            val altura = etAltura.text.toString().toDoubleOrNull() ?: 0.0
            val latitud = etLatitud.text.toString().toDoubleOrNull() ?: 0.0
            val longitud = etLongitud.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty() && nacionalidad.isNotEmpty()) {
                val exito = databaseHelper.insertarActor(nombre, nacionalidad, edad, altura, latitud, longitud)
                if (exito) {
                    Toast.makeText(this, "Actor guardado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
