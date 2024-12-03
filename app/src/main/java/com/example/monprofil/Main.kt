package com.example.monprofil

fun main(){

    var macuisine = Cuisine()
    var monsalon = Salon()


    val liste = listOf(macuisine, monsalon)

    for(item in liste) {

        println(item.nom)
        println(item.surface())

    }
}