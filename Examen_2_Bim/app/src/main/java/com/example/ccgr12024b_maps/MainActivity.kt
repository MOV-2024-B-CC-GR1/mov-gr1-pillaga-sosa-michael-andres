package com.example.ccgr12024b_maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ListView
import android.content.Intent
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var btnVerMapa: Button  // ✅ Variable declarada correctamente
    private lateinit var btnVerDetalles: Button  // ✅ Variable declarada correctamente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        listView = findViewById(R.id.listViewActores)
        val btnAgregar: Button = findViewById(R.id.btnAgregarActor)
        btnVerDetalles = findViewById(R.id.btnVerDetalles)  // ✅ Inicialización corregida
        btnVerMapa = findViewById(R.id.btnVerMapa)  // ✅ Ahora está inicializado correctamente

        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarActorActivity::class.java))
        }

        btnVerMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }

        btnVerDetalles.setOnClickListener {
            val intent = Intent(this, DetalleActorActivity::class.java)
            startActivity(intent)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val actores = databaseHelper.obtenerActores()
            val actor = actores[position]

            val intent = Intent(this, DetalleActorActivity::class.java)
            intent.putExtra("ACTOR_ID", actor.id)
            intent.putExtra("NOMBRE", actor.nombre)
            intent.putExtra("NACIONALIDAD", actor.nacionalidad)
            intent.putExtra("EDAD", actor.edad)
            intent.putExtra("ALTURA", actor.altura)
            intent.putExtra("LATITUD", actor.latitud)
            intent.putExtra("LONGITUD", actor.longitud)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }

    private fun actualizarLista() {
        val actores = databaseHelper.obtenerActores()
        val nombresActores = actores.map { it.nombre }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresActores)
        listView.adapter = adapter
    }
}

