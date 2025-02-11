package com.example.examen2bim_michaelpillaga

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
        val etEdad: EditText = findViewById(R.id.etEdad)
        val etNacionalidad: EditText = findViewById(R.id.etNacionalidad)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val edad = etEdad.text.toString().toIntOrNull()
            val nacionalidad = etNacionalidad.text.toString()

            if (nombre.isNotEmpty() && edad != null) {
                val exito = databaseHelper.insertarActor(nombre, edad, nacionalidad)
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
