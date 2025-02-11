package com.example.examen2bim_michaelpillaga

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        listView = findViewById(R.id.listViewActores)
        val btnAgregar: Button = findViewById(R.id.btnAgregarActor)

        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarActorActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val actores = databaseHelper.obtenerActores()
            databaseHelper.eliminarActor(actores[position].id)
            actualizarLista()
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
