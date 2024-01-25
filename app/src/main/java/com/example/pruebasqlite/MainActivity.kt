package com.example.pruebasqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)
        val botonadd = findViewById<Button>(R.id.Botonadd)
        val botonVisualizar = findViewById<Button>(R.id.Botonvisualizar)

        botonadd.setOnClickListener(){
        val objeto = Mochila(KEY_ID = 1, TipoArticulo = "Arma", Nombre = "Espada", peso = 5, precio = 20)
        dbHelper.addArticulo(objeto)
            Toast.makeText(this,"Objeto GUardado",Toast.LENGTH_LONG).show()
        }

        botonVisualizar.setOnClickListener(){

        }
    }
}