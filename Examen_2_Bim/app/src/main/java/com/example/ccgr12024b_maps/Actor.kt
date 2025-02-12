package com.example.ccgr12024b_maps

// Entidad Actor
data class Actor(
    val id: Int,
    val nombre: String,
    val nacionalidad: String,
    val edad: Int,    // Nuevo campo
    val altura: Double,  // Nuevo campo
    val latitud: Double,    // Nuevo campo
    val longitud: Double    // Nuevo campo
)

// Entidad PeliculaActor
data class PeliculaActor(
    val id: Int,
    val actorId: Int,
    val nombrePelicula: String
)

// Simulación de base de datos
object BaseDeDatos {
    val actores = mutableListOf<Actor>()
    val peliculasActor = mutableListOf<PeliculaActor>()

    fun agregarActor(actor: Actor) {
        actores.add(actor)
    }

    fun obtenerActores(): List<Actor> {
        return actores
    }

    fun eliminarActor(id: Int) {
        actores.removeIf { it.id == id }
        peliculasActor.removeIf { it.actorId == id }
    }

    fun agregarPelicula(pelicula: PeliculaActor) {
        peliculasActor.add(pelicula)
    }

    fun obtenerPeliculas(actorId: Int): List<PeliculaActor> {
        return peliculasActor.filter { it.actorId == actorId }
    }
}
