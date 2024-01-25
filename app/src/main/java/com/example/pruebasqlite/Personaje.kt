package com.example.pruebasqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,DATABASE,null,DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION =1
        private const val DATABASE = "Mochila.db"
        private const val TABLA_MOCHILA = "Mochila"
        private const val KEY_ID = "_id"
        private const val COLUMN_TIPO_ARTICULO="TipoArticulo"
        private const val COLUMN_NOMBRE="Nombre"
        private const val COLUMN_PESO="Peso"
        private const val COLUMN_PRECIO="Precio"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLA_MOCHILA($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TIPO_ARTICULO TEXT, $COLUMN_NOMBRE TEXT, $COLUMN_PESO INTEGER," +
                "$COLUMN_PRECIO INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
        onCreate(db)
    }

    fun addArticulo(mochila: Mochila){
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TIPO_ARTICULO,mochila.getTipoArticulo().toString())
            put(COLUMN_NOMBRE,mochila.getNombre().toString())
            put(COLUMN_PESO,mochila.getPeso().toString().toInt())
            put(COLUMN_PRECIO,mochila.getPrecio().toString().toInt())
        }
        db.insert(TABLA_MOCHILA,null,values)
        db.close()
    }

    fun getNumeroArticulos():Int{
        return KEY_ID.toString().toInt()
    }

    @SuppressLint("Range")
    fun getContenido(): ArrayList<Mochila>{
        val objetos = ArrayList<Mochila>()
        val selectQuery = "SELECT * FROM $TABLA_MOCHILA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val TipoArticulo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
                val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
                objetos.add(Mochila(id,TipoArticulo, nombre,peso,precio))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return objetos
    }


    /*
    fun findObjeto():Boolean {
        if (COLUMN_NOMBRE.equals(Articulo.Nombre){
            return true
        }else return false
    }

     */
}

class Personaje(
    private var nombre: String,
    private val raza: Raza,
    private var clase: Clase,
    private var estadoVital: EstadoVital
) {
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var suerte: Int
    private var defensa: Int = 0

    // Enumeración para el tipo de raza y clase
    enum class Raza { Humano, Elfo, Enano, Maldito }
    enum class Clase { Brujo, Mago, Guerrero }
    enum class EstadoVital{Anciano, Joven, Adulto}

    // Ejemplo de peso máximo de la mochila
    // Atributos para el equipo del personaje
    private var arma: Articulo? = null
    private var proteccion: Articulo? = null

    // Inicialización de los atributos tras la construcción del objeto Personaje
    init {
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
        experiencia = 0
        nivel = 1
        suerte = (0..10).random() // Asigna un valor de suerte aleatorio entre 0 y 10
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }
    fun getRaza(): Raza {
        return raza
    }
    fun getSalud(): Int {
        return salud
    }
    fun setSalud(nuevaSalud: Int) {
        salud = nuevaSalud
    }
    fun getAtaque(): Int {
        return ataque
    }
    fun setAtaque(nuevoAtaque: Int) {
        ataque = nuevoAtaque
    }
    fun getClase(): Clase {
        return clase
    }
    fun setClase(nuevaClase: Clase) {
        clase = nuevaClase
    }
    fun getEstadoVital(): EstadoVital {
        return estadoVital
    }
    fun setEstadoVital(nuevoEstadoVital: EstadoVital) {
        estadoVital = nuevoEstadoVital
    }
    fun getExperiencia(): Int {
        return experiencia
    }
    fun setExperiencia(experienciaGanada: Int) {
        experiencia += experienciaGanada
        while (experiencia >= 1000) {
            subirNivel()
            experiencia -= 1000 // Reducir la experiencia en 1000 al subir de nivel
        }
    }
    fun getNivel(): Int {
        return nivel
    }
    fun subirNivel() {
        if (nivel < 10) { // Limitar el nivel a 10
            nivel++
            calcularSalud() // Calcular el nuevo valor de salud al subir de nivel
            calcularAtaque() // Calcular el nuevo valor de ataque al subir de nivel
            calcularDefensa()
        }
    }
    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 200
            3 -> 300
            4 -> 450
            5 -> 600
            6 -> 800
            7 -> 1000
            8 -> 1250
            9 -> 1500
            10 -> 2000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 10
            2 -> 20
            3 -> 25
            4 -> 30
            5 -> 40
            6 -> 100
            7 -> 200
            8 -> 350
            9 -> 400
            10 -> 450
            else -> 10 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    private fun calcularDefensa() {
        defensa = when (nivel) {
            1 -> 4
            2 -> 9
            3 -> 14
            4 -> 19
            5 -> 49
            6 -> 59
            7 -> 119
            8 -> 199
            9 -> 349
            10 -> 399
            else -> 4 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }


    fun habilidad() {
        when (clase) {
            Clase.Mago -> {
                calcularSalud() // Subir la salud al límite del nivel
                println("$nombre utiliza su habilidad de Mago para restaurar su salud.")
            }
            Clase.Brujo -> {
                ataque *= 2 // Duplicar el ataque
                println("$nombre utiliza su habilidad de Brujo para duplicar su ataque.")
            }
            Clase.Guerrero -> {
                suerte *= 2 // Duplicar la suerte
                println("$nombre utiliza su habilidad de Guerrero para duplicar su suerte.")
            }
        }
    }
    fun entrenar(tiempoDeEntrenamiento: Int) {
        val factorExperienciaPorHora = 5
        val experienciaGanada = tiempoDeEntrenamiento * factorExperienciaPorHora

        setExperiencia(experienciaGanada)

        println("$nombre ha entrenado durante $tiempoDeEntrenamiento horas y ha ganado $experienciaGanada de experiencia.")
    }
    fun realizarMision(tipoMision: String, dificultad: String) {
        val probabilidadExito = when (dificultad) {
            "Fácil" -> if (nivel >= 5) 8 else 6
            "Normal" -> if (nivel >= 3) 6 else 4
            "Difícil" -> if (nivel >= 7) 4 else 2
            else -> 0 // En caso de dificultad no reconocida
        }

        val resultado = (1..10).random() // Valor aleatorio entre 1 y 10

        if (resultado <= probabilidadExito) {
            val experienciaGanada = when (tipoMision) {
                "Caza" -> 1000
                "Búsqueda" -> 500
                "Asedio" -> 2000
                "Destrucción" -> 5000
                else -> 0 // En caso de tipo de misión no reconocido
            }

            val multiplicadorExperiencia = when (dificultad) {
                "Fácil" -> 0.5
                "Normal" -> 1.0
                "Difícil" -> 2.0
                else -> 0.0 // En caso de dificultad no reconocida
            }

            val experienciaFinal = (experienciaGanada * multiplicadorExperiencia).toInt()
            setExperiencia(experienciaFinal)
            println("$nombre ha completado la misión de $tipoMision ($dificultad) con éxito y gana $experienciaFinal de experiencia.")
        } else {
            println("$nombre ha fracasado en la misión de $tipoMision ($dificultad) y no recibe ninguna recompensa.")
        }
    }
    fun cifrado(mensaje : String, ROT : Int) : String{
        val abecedario : ArrayList<Char> = "abcdefghijklmnñopqrstuvwxyz".toList() as ArrayList<Char>
        var stringInv = ""
        var indice = 0

        for(i in mensaje.lowercase().toList() as ArrayList<Char>){
            if(!i.isLetter() || i.isWhitespace()){
                stringInv += i
            } else{
                indice = abecedario.indexOf(i) + ROT
                if (indice >= abecedario.size) {
                    indice -= abecedario.size
                    stringInv += abecedario[indice]
                } else
                    stringInv += abecedario[indice]
            }
        }
        return stringInv.filter { !it.isWhitespace() && it.isLetter() }
    }
    fun comunicacion(mensaje:String){
        var respuesta=""
        when(estadoVital){
            EstadoVital.Adulto->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="En la flor de la vida, pero me empieza a doler la espalda"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Estoy buscando la mejor solución"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="No me levantes la voz mequetrefe"
                        else
                            if (mensaje == nombre)
                                respuesta="¿Necesitas algo?"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    if (arma != null || proteccion != null) {
                                        val equipamiento = mutableListOf<String>()
                                        if (arma != null) {
                                            equipamiento.add(arma!!.getNombre().name)
                                        }
                                        if (proteccion != null) {
                                            equipamiento.add(proteccion!!.getNombre().name)
                                        }
                                        println("Tengo equipado: ${equipamiento.joinToString(", ")}")
                                    } else {
                                        println("Ligero como una pluma.")
                                    }
                                }
                                else
                                    respuesta="No sé de qué me estás hablando"
            }
            EstadoVital.Joven->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="De lujo"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Tranqui se lo que hago"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="Eh relájate"
                        else
                            if (mensaje == nombre)
                                respuesta="Qué pasa?"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    if (arma != null || proteccion != null) {
                                        println("No llevo nada, agente, se lo juro.")
                                    } else {
                                        println("Regístrame si quieres.")
                                    }
                                }
                                else
                                    respuesta="Yo que se"

            }
            EstadoVital.Anciano->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="No me puedo mover"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Que no te escucho!"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="Háblame más alto que no te escucho"
                        else
                            if (mensaje == nombre)
                                respuesta="Las 5 de la tarde"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    println("A ti que te importa nini!")
                                }
                                else
                                    respuesta="En mis tiempos esto no pasaba"
            }
        }
        when(raza){
            Raza.Elfo-> println(cifrado(respuesta, 1))
            Raza.Enano-> println(respuesta.uppercase())
            Raza.Maldito-> println(cifrado(respuesta, 1))
            else -> println(respuesta)
        }
    }


    override fun toString(): String {
        return "Personaje: Nombre: $nombre, Nivel: $nivel, Salud: $salud, Ataque: $ataque, Defensa: $defensa, Suerte: $suerte, Raza: $raza, Clase: $clase, Estado Vital: $estadoVital "
    }

}
class Mochila {
    private var KEY_ID: Int
    private var TipoArticulo: String
    private var Nombre: String
    private var peso: Int
    private var precio: Float

    constructor(KEY_ID: Int, TipoArticulo: String, Nombre: String, peso: Int, precio: Int) {
        this.KEY_ID = KEY_ID
        this.TipoArticulo = TipoArticulo
        this.Nombre = Nombre
        this.peso = peso
        this.precio = precio.toFloat()
    }
    fun getPeso(): Int {
        return peso
    }
    fun getPrecio(): Float {
        return precio
    }
    fun getNombre() : String {
        return Nombre
    }
    fun getTipoArticulo(): String {
        return TipoArticulo
    }

    override fun toString(): String {
        return "Mochila(KEY_ID=$KEY_ID, TipoArticulo='$TipoArticulo', Nombre='$Nombre', peso=$peso, precio=$precio)"
    }



}
class Articulo(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int, private var precio: Float) {

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION }
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA }

    fun getPeso(): Int {
        return peso
    }
    fun getPrecio(): Float {
        return precio
    }
    fun getNombre(): Nombre {
        return nombre
    }
    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }
    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0 // Para otros tipos de armas no especificados
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }
    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}
