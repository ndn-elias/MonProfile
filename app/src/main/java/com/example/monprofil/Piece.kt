package com.example.monprofil

open abstract class Piece {
    abstract var largeur: Float
    abstract var longueur: Float
    abstract var nom: String


    fun surface(): Float {
        val surfacetotale = largeur * longueur
        return surfacetotale
    }

}