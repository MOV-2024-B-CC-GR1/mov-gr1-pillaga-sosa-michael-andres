package com.example.examen2bim_michaelpillaga

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ActoresDB"
        private const val DATABASE_VERSION = 2
        private const val TABLE_ACTORES = "actores"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EDAD = "edad"
        private const val COLUMN_NACIONALIDAD = "nacionalidad"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_ACTORES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_EDAD INTEGER, " +
                "$COLUMN_NACIONALIDAD TEXT) "
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS actores")  // Elimina la tabla antigua
        onCreate(db)  // Llama a onCreate para crear la nueva tabla con nacionalidad
    }


    fun insertarActor(nombre: String, edad: Int, nacionalidad: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EDAD, edad)
            put(COLUMN_NACIONALIDAD, nacionalidad)
        }
        val result = db.insert(TABLE_ACTORES, null, values)
        db.close()
        return result != -1L
    }

    fun obtenerActores(): List<Actor> {
        val listaActores = mutableListOf<Actor>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACTORES", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
                val nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NACIONALIDAD))
                listaActores.add(Actor(id, nombre, edad, nacionalidad))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaActores
    }

    fun eliminarActor(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_ACTORES, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
