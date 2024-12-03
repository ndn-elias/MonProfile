package com.example.monprofil

import kotlinx.serialization.Serializable

@Serializable
data class TMDBListeDesFilms(
    val page: Int = 1,
    val results: List<AfficheDeFilm> = emptyList(),
    val total_pages: Int = 1,
    val total_results: Int = 0
)

@Serializable
data class AfficheDeFilm(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val media_type: String = "movie",
    val original_language: String = "fr",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    val credits: Credits? = null // Ajout des crédits
)

@Serializable
data class Credits(
    val cast: List<CastMember> = emptyList(), // Liste des acteurs
    val crew: List<CrewMember> = emptyList()  // Liste des membres de l'équipe
)

@Serializable
data class CastMember(
    val id: Int,
    val name: String,
    val character: String, // Nom du personnage joué par l'acteur
    val profile_path: String? // URL de l'image du profil de l'acteur
)

@Serializable
data class CrewMember(
    val id: Int,
    val name: String,
    val job: String, // Rôle dans la production du film (ex : réalisateur)
    val department: String, // Département (ex : direction, production)
    val profile_path: String? // URL de l'image du profil du membre de l'équipe
)

@Serializable
data class TMDBListeDesSeries(
    val results: List<Serie>
)

@Serializable
data class Serie(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val credits: Credits? = null
)

@Serializable
data class TMDBListeDesActeurs(
    val results: List<Acteur>
)

@Serializable
data class Acteur(
    val id: Int,
    val name: String,
    val profile_path: String? // URL de l'image du profil
)
