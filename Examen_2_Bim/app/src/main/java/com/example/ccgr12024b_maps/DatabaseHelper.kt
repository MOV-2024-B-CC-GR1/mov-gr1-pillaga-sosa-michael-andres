package com.example.ccgr12024b_maps

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "actores.db"
        private const val DATABASE_VERSION = 4 // 🔹 Incrementar la versión

        const val TABLE_ACTORES = "actor"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_NACIONALIDAD = "nacionalidad"
        const val COLUMN_EDAD = "edad"
        const val COLUMN_ALTURA = "altura"
        const val COLUMN_LATITUD = "latitud"
        const val COLUMN_LONGITUD = "longitud"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_ACTORES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_NACIONALIDAD TEXT,
                $COLUMN_EDAD INTEGER,
                $COLUMN_ALTURA REAL,
                $COLUMN_LATITUD REAL,
                $COLUMN_LONGITUD REAL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) { // 🔹 Asegurar versión más reciente
            if (!columnaExiste(db, TABLE_ACTORES, COLUMN_EDAD)) {
                db.execSQL("ALTER TABLE $TABLE_ACTORES ADD COLUMN $COLUMN_EDAD INTEGER DEFAULT 0")
            }
            if (!columnaExiste(db, TABLE_ACTORES, COLUMN_ALTURA)) {
                db.execSQL("ALTER TABLE $TABLE_ACTORES ADD COLUMN $COLUMN_ALTURA REAL DEFAULT 0.0")
            }
            if (!columnaExiste(db, TABLE_ACTORES, COLUMN_LATITUD)) {
                db.execSQL("ALTER TABLE $TABLE_ACTORES ADD COLUMN $COLUMN_LATITUD REAL DEFAULT 0.0")
            }
            if (!columnaExiste(db, TABLE_ACTORES, COLUMN_LONGITUD)) {
                db.execSQL("ALTER TABLE $TABLE_ACTORES ADD COLUMN $COLUMN_LONGITUD REAL DEFAULT 0.0")
            }
        }
    }

    private fun columnaExiste(db: SQLiteDatabase, tableName: String, columnName: String): Boolean {
        val cursor = db.rawQuery("PRAGMA table_info($tableName)", null)
        var existe = false
        if (cursor.moveToFirst()) {
            do {
                val nombreColumna = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                if (nombreColumna == columnName) {
                    existe = true
                    break
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return existe
    }

    fun insertarActor(nombre: String, nacionalidad: String, edad: Int, altura: Double, latitud: Double, longitud: Double): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_NACIONALIDAD, nacionalidad)
        values.put(COLUMN_EDAD, edad)
        values.put(COLUMN_ALTURA, altura)
        values.put(COLUMN_LATITUD, latitud)
        values.put(COLUMN_LONGITUD, longitud)

        val resultado = db.insert(TABLE_ACTORES, null, values)
        return resultado != -1L
    }

    fun obtenerActores(): List<Actor> {
        val listaActores = mutableListOf<Actor>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACTORES", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NACIONALIDAD))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
                val altura = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ALTURA))
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUD))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUD))
                listaActores.add(Actor(id, nombre, nacionalidad, edad, altura, latitud, longitud))
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