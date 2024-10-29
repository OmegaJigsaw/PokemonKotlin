package com.example.pokemon.Pokemon

data class Pokemon (
    // Definir parametros por defecto para no tener problemas cuando se tenga que crear el objeto puro sin datos automaticos
    val nombre: String = "", val tipo: String = "", val ataque: Int = 0, val defensa: Int = 0
)
