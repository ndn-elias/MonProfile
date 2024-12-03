package com.example.monprofil

fun main(){

    var macuisine = Cuisine()
    var monsalon = Salon()


    val liste = listOf(macuisine, monsalon)

    for(item in liste) {

        println(item.nom)
        println(item.surface())

    }
    val etudiants = listOf(
        Etudiant("Paul", "2025", listOf("mobile", "web", "reseau")),
        Etudiant("Yazid", "2024", listOf("mobile", "android", "reseau")),
        Etudiant("Caroline", "2025", listOf("Anglais", "SE"))
    )


    etudiants.filter { it.promo == "2024"}.forEach { println(it.name) }

    for (etudiant in etudiants){
        if (etudiant.matieres.count()>2){
            println(etudiant.name)
        }
    }

    var nbmatieres : Int = 0
    for (etudiant in etudiants){
        nbmatieres = nbmatieres + etudiant.matieres.count()
        }
    println(nbmatieres)



}