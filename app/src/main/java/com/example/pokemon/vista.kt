package com.example.pokemon

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.Pokemon.Pokemon
import com.example.pokemon.Pokemon.pokemon_adapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class vista : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista)

        val btnBack = findViewById<Button>(R.id.back)
        btnBack.setOnClickListener{
            finish()
        }

        val lvPokemon = findViewById<ListView>(R.id.lvpokemon)

        database = Firebase.database.reference

        val coleccion  = database.child("pokemons")

        val pokemonListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaPokemons = ArrayList<Pokemon>()
                for (i in snapshot.children){
                    val pokemon = i.getValue(Pokemon::class.java) ?: Pokemon()
                    listaPokemons.add(pokemon)
                }
                val adapter = pokemon_adapter(context, R.layout.pokemon_cards, listaPokemons)
                lvPokemon.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        coleccion.addValueEventListener(pokemonListener)

    }
}
