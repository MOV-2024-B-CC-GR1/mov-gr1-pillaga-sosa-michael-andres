import java.io.File

data class Pelicula(
    val titulo: String,
    val anio: Int,
    val genero: String,
    val duracion: Double,
    val clasificacion: String
)

data class Actor(
    val nombre: String,
    val edad: Int,
    val esPrincipal: Boolean,
    val nacionalidad: String,
    val salario: Double
)

object CRUD {
    private const val ACTORES_FILE = "src/data/actores.txt"
    private const val PELICULAS_FILE = "src/data/peliculas.txt"

    private fun leerArchivo(ruta: String): List<String> = File(ruta).readLines()
    private fun escribirArchivo(ruta: String, contenido: List<String>) = File(ruta).writeText(contenido.joinToString("\n"))

    fun crearActor(actor: Actor) {
        val nuevaLinea = "${actor.nombre},${actor.edad},${actor.esPrincipal},${actor.nacionalidad},${actor.salario}"
        val contenido = leerArchivo(ACTORES_FILE).toMutableList()
        contenido.add(nuevaLinea)
        escribirArchivo(ACTORES_FILE, contenido)
        println("Actor creado: $actor")
    }

    fun leerActores(): List<Actor> {
        val lineas = leerArchivo(ACTORES_FILE)
        return lineas.mapNotNull { linea ->
            val datos = linea.split(",")
            if (datos.size == 5) {
                val nombre = datos[0]
                val edad = datos[1].toIntOrNull() ?: return@mapNotNull null
                val esPrincipal = datos[2].toBooleanStrictOrNull() ?: return@mapNotNull null
                val nacionalidad = datos[3]
                val salario = datos[4].toDoubleOrNull() ?: return@mapNotNull null
                Actor(nombre, edad, esPrincipal, nacionalidad, salario)
            } else {
                println("Línea inválida en archivo de actores: $linea")
                null
            }
        }
    }

    fun actualizarActor(nombre: String, nuevoActor: Actor) {
        val actores = leerActores().toMutableList()
        val index = actores.indexOfFirst { it.nombre == nombre }
        if (index != -1) {
            actores[index] = nuevoActor
            escribirArchivo(ACTORES_FILE, actores.map { "${it.nombre},${it.edad},${it.esPrincipal},${it.nacionalidad},${it.salario}" })
            println("Actor actualizado: $nuevoActor")
        } else {
            println("Actor no encontrado.")
        }
    }

    fun eliminarActor(nombre: String) {
        val actores = leerActores().toMutableList()
        val index = actores.indexOfFirst { it.nombre == nombre }
        if (index != -1) {
            val eliminado = actores.removeAt(index)
            escribirArchivo(ACTORES_FILE, actores.map { "${it.nombre},${it.edad},${it.esPrincipal},${it.nacionalidad},${it.salario}" })
            println("Actor eliminado: $eliminado")
        } else {
            println("Actor no encontrado.")
        }
    }

    fun agregarPeliculaAActor(actorNombre: String, pelicula: Pelicula) {
        val peliculas = leerArchivo(PELICULAS_FILE).toMutableList()
        peliculas.add("${pelicula.titulo},${pelicula.anio},${pelicula.genero},${pelicula.duracion},${pelicula.clasificacion},$actorNombre")
        escribirArchivo(PELICULAS_FILE, peliculas)
        println("Película agregada: ${pelicula.titulo} al actor $actorNombre")
    }

    fun leerPeliculasPorActor(nombreActor: String): List<Pelicula> {
        val lineas = leerArchivo(PELICULAS_FILE)
        return lineas.mapNotNull { linea ->
            val datos = linea.split(",")
            if (datos.size == 6 && datos[5] == nombreActor) {
                Pelicula(
                    datos[0],
                    datos[1].toIntOrNull() ?: return@mapNotNull null,
                    datos[2],
                    datos[3].toDoubleOrNull() ?: return@mapNotNull null,
                    datos[4]
                )
            } else {
                null
            }
        }
    }
}

fun main() {
    while (true) {
        println("\n--- Menú ---")
        println("1. Crear Actor")
        println("2. Leer Actores")
        println("3. Actualizar Actor")
        println("4. Eliminar Actor")
        println("5. Agregar Película a Actor")
        println("6. Leer Películas por Actor")
        println("7. Salir")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                println("Ingrese los datos del actor:")
                print("Nombre: "); val nombre = readln()
                print("Edad: "); val edad = readln().toInt()
                print("¿Es principal? (true/false): "); val esPrincipal = readln().toBoolean()
                print("Nacionalidad: "); val nacionalidad = readln()
                print("Salario: "); val salario = readln().toDouble()

                val actor = Actor(nombre, edad, esPrincipal, nacionalidad, salario)
                CRUD.crearActor(actor)
            }

            2 -> {
                println("Lista de Actores:")
                CRUD.leerActores().forEach { println(it) }
            }

            3 -> {
                println("Ingrese el nombre del actor a actualizar:")
                val nombre = readln()
                println("Ingrese los nuevos datos del actor:")
                print("Nombre: "); val nuevoNombre = readln()
                print("Edad: "); val edad = readln().toInt()
                print("¿Es principal? (true/false): "); val esPrincipal = readln().toBoolean()
                print("Nacionalidad: "); val nacionalidad = readln()
                print("Salario: "); val salario = readln().toDouble()

                val nuevoActor = Actor(nuevoNombre, edad, esPrincipal, nacionalidad, salario)
                CRUD.actualizarActor(nombre, nuevoActor)
            }

            4 -> {
                println("Ingrese el nombre del actor a eliminar:")
                val nombre = readln()
                CRUD.eliminarActor(nombre)
            }

            5 -> {
                println("Ingrese el nombre del actor:")
                val nombreActor = readln()
                println("Ingrese los datos de la película:")
                print("Título: "); val titulo = readln()
                print("Año: "); val anio = readln().toInt()
                print("Género: "); val genero = readln()
                print("Duración (en horas): "); val duracion = readln().toDouble()
                print("Clasificación: "); val clasificacion = readln()

                val pelicula = Pelicula(titulo, anio, genero, duracion, clasificacion)
                CRUD.agregarPeliculaAActor(nombreActor, pelicula)
            }

            6 -> {
                println("Ingrese el nombre del actor:")
                val nombreActor = readln()
                val peliculas = CRUD.leerPeliculasPorActor(nombreActor)
                if (peliculas.isEmpty()) {
                    println("No se encontraron películas para el actor $nombreActor.")
                } else {
                    println("Películas de $nombreActor:")
                    peliculas.forEach { println("  - ${it.titulo} (${it.anio}, ${it.genero}, ${it.duracion} horas, ${it.clasificacion})") }
                }
            }

            7 -> {
                println("¡Hasta luego!")
                break
            }

            else -> println("Opción no válida.")
        }
    }
}
