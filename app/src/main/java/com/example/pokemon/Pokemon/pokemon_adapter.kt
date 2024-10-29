package com.example.pokemon.Pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pokemon.R

class pokemon_adapter (
    context: Context, val resource: Int, val listaObjetos: List<Pokemon>
) : ArrayAdapter<Pokemon>(context, resource, listaObjetos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val vista = convertView ?: LayoutInflater.from(context).inflate(resource,parent,false)
        val pokemon = listaObjetos[position]

        val nombre = vista.findViewById<TextView>(R.id.nombre)
        val tipo = vista.findViewById<TextView>(R.id.tipo)
        val ataque = vista.findViewById<TextView>(R.id.ataque)
        val defensa = vista.findViewById<TextView>(R.id.defensa)

        nombre.text = "Pokemon: ${pokemon.nombre}"
        tipo.text = "Tipo: ${pokemon.tipo}"
        ataque.text = "ATK: ${pokemon.ataque}"
        defensa.text = "DFE: ${pokemon.defensa}"

        return vista
    }
}
