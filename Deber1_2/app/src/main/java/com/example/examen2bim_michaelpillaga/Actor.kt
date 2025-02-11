package com.example.examen2bim_michaelpillaga

// Entidad Actor
data class Actor(
    val id: Int = 0,
    val nombre: String,
    val edad: Int,
    val nacionalidad: String
)

// Entidad Pelicula
data class Pelicula(
    val id: Int,
    val actorId: Int,
    val titulo: String
)

// Simulación de base de datos
object BaseDeDatos {
    val actores = mutableListOf<Actor>()
    val peliculas = mutableListOf<Pelicula>()

    fun agregarActor(actor: Actor) {
        actores.add(actor)
    }

    fun obtenerActores(): List<Actor> {
        return actores
    }

    fun eliminarActor(id: Int) {
        actores.removeIf { it.id == id }
        peliculas.removeIf { it.actorId == id }
    }

    fun agregarPelicula(pelicula: Pelicula) {
        peliculas.add(pelicula)
    }

    fun obtenerPeliculas(actorId: Int): List<Pelicula> {
        return peliculas.filter { it.actorId == actorId }
    }
}
