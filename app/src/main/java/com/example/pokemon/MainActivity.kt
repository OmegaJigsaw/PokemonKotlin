package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.Pokemon.Pokemon
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val nombre = findViewById<EditText>(R.id.nombre)
        val tipo = findViewById<EditText>(R.id.tipo)
        val ataque = findViewById<EditText>(R.id.ataque)
        val defensa = findViewById<EditText>(R.id.defensa)

        database  = Firebase.database.reference

        val btnSend = findViewById<Button>(R.id.send)
        btnSend.setOnClickListener{
            // PARA MANEJAR ERRORES COMO EN DJANGO ES UN MUTABLEMAP<TipoDatoClave, TipoDatoValor>
            val has_error: MutableMap<String,String> = mutableMapOf()

            if (nombre.text.toString() == "")
                has_error["nombre_vacio"] = "Campo nombre vacio"
            if (tipo.text.toString() == "")
                has_error["tipo_vacio"] = "Campo tipo vacio"

            if (ataque.text.toString() == "")
                has_error["ataque_vacio"] = "Campo ataque vacio"
            else if (ataque.text.toString().toIntOrNull() == null ){
                has_error["ataque_tipo_dato"] = "El ataque debe ser un numero entero"
            }
            if (defensa.text.toString() == "")
                has_error["defensa_vacio"] = "Campo defensa vacio"
            else if (defensa.text.toString().toIntOrNull() == null){
                has_error["defensa_tipo_dato"] = "La defensa debe ser un numero entero"
            }

            if (has_error.isEmpty()){
                val pokemon = Pokemon(
                    nombre.text.toString(),
                    tipo.text.toString(),
                    ataque.text.toString().toInt(),
                    defensa.text.toString().toInt()
                )
                database.child("pokemons").push().setValue(pokemon)
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()

                nombre.text.clear()
                tipo.text.clear()
                ataque.text.clear()
                defensa.text.clear()
            }
            else
            {
                // Variable que junta y guarda los errores del diccionario en una cadena de texto
                val errores = has_error.values.joinToString("\n")

                // ESTO ES NUEVO
                // ALERTDIALOG ES UN OBJETO QUE COMO DICE EL NOMBRE LEVANTA ALERTAS
                // PARA CREARLO PRIMERO SE IMPORTA COMO TAL ALERTDIALOG Y SE LLAMA AL BUILDER QUE ES QUIEN LO CREA
                // PARA QUE FUNCIONE HAY QUE PASARLE AL METODO BUILDER LA PESTAÑA DONDE SE MUESTRA
                AlertDialog.Builder(this)
                    // LUEGO SE COLOCA EL TITULO DE LA CARD DE LA ALERTA CON .setTitle
                    .setTitle("Error al ingresar")
                    // EN ESTE CASO PARA PASAR LOS ERRORES EN .setMessage("aqui va el texto o en este caso la variable de errores")
                    .setMessage(errores)
                    // ESTO SON LOS BTN QUE TIENE LA ALERTA, POSTIVE PARA CONFIRMAR | NEGATIVE PARA CANCELAR | NEUTRAL PARA IGNORAR

                    // EL PARAM 1 ES EL TEXTO DEL BOTON

                    // AHORA LO COMPLICADO, LO QUE ESTA A LA DERECHA ES UNA FUNCION ANONIMA QUE FUNCIONA COMO UN ¡LISTENER!
                    // dialog hace referencia al mismo cuadro que tiene el mensaje, _ es el evento que levanta el listener, pero como no lo hay se usa ese simbolo
                    .setPositiveButton("Cerrar") { dialog, _ ->
                        //  dialog.dismiss cierra el dialogo con los errores como lo hace el metodo finish()
                        dialog.dismiss()
                    }
                    // ESTO ES LA FUNCION QUE LEVANTA EL MENSAJE
                    .show()
            }
        }
        val btnView = findViewById<Button>(R.id.view)
        btnView.setOnClickListener{
            val intent = Intent(this, vista::class.java)
            startActivity(intent)
        }


    }
}