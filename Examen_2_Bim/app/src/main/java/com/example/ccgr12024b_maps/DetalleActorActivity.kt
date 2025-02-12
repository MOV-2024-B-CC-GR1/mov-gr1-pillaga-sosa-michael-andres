package com.example.ccgr12024b_maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleActorActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var actorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_actor)

        databaseHelper = DatabaseHelper(this)

        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvNacionalidad: TextView = findViewById(R.id.tvNacionalidad)
        val tvEdad: TextView = findViewById(R.id.tvEdad)
        val tvAltura: TextView = findViewById(R.id.tvAltura)
        val tvLatitud: TextView = findViewById(R.id.tvLatitud)
        val tvLongitud: TextView = findViewById(R.id.tvLongitud)
        val btnEliminar: Button = findViewById(R.id.btnEliminar)
        val btnModificar: Button = findViewById(R.id.btnModificar)
        val btnVerMapa: Button = findViewById(R.id.btnVerMapa)

        // Recibir datos del Intent
        actorId = intent.getIntExtra("ACTOR_ID", -1)
        val nombre = intent.getStringExtra("NOMBRE") ?: "No disponible"
        val nacionalidad = intent.getStringExtra("NACIONALIDAD") ?: "No disponible"
        val edad = intent.getIntExtra("EDAD", 0)
        val altura = intent.getDoubleExtra("ALTURA", 0.0)
        val latitud = intent.getDoubleExtra("LATITUD", 0.0)
        val longitud = intent.getDoubleExtra("LONGITUD", 0.0)

        // Mostrar los datos en la interfaz
        tvNombre.text = "Nombre: $nombre"
        tvNacionalidad.text = "Nacionalidad: $nacionalidad"
        tvEdad.text = "Edad: $edad"
        tvAltura.text = "Altura: $altura"
        tvLatitud.text = "Latitud: $latitud"
        tvLongitud.text = "Longitud: $longitud"

        // Botón eliminar
        btnEliminar.setOnClickListener {
            databaseHelper.eliminarActor(actorId)
            finish() // Cierra la actividad y vuelve a la lista
        }

        // Botón modificar (puede abrir una nueva actividad para editar)
        btnModificar.setOnClickListener {
            val intent = Intent(this, AgregarActorActivity::class.java)
            intent.putExtra("ACTOR_ID", actorId)
            startActivity(intent)

            // Botón ver mapa
            btnVerMapa.setOnClickListener {
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra("LATITUD", latitud)
                intent.putExtra("LONGITUD", longitud)
                startActivity(intent)
            }
        }
    }
}