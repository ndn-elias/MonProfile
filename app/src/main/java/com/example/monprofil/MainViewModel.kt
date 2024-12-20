package com.example.monprofil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.playlistjson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Initialisation de Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

// Création de l'instance de l'API
val api = retrofit.create(Api::class.java)

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<AfficheDeFilm>>(emptyList())
    val series = MutableStateFlow<List<Serie>>(emptyList())
    val acteurs = MutableStateFlow<List<Acteur>>(emptyList())
    val api_key = "be1ca8af0da3936dcdb2aeaad464d374"
    val movieDetails = MutableStateFlow<AfficheDeFilm?>(null)
    val serieDetails = MutableStateFlow<Serie?>(null)
    val acteurDetails = MutableStateFlow<Acteur?>(null)



    val maplaylist = MutableStateFlow<Playlist?>(null)

    // Fonction pour récupérer les films populaires
    fun getMovies() {
        viewModelScope.launch {
            try {
                val response = api.lastmovies(api_key)
                if (response.results.isNotEmpty()) {
                    movies.value = response.results
                } else {
                    movies.value = emptyList()
                }
            } catch (e: Exception) {
                movies.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Fonction pour rechercher des films par mot-clé
    fun searchMovies(searchQuery: String) {
        viewModelScope.launch {
            try {
                val response = api.searchMovies(api_key, searchQuery)
                if (response.results.isNotEmpty()) {
                    movies.value = response.results
                } else {
                    movies.value = emptyList()
                }
            } catch (e: Exception) {
                movies.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Fonction pour récupérer les séries populaires
    fun getSeries() {
        viewModelScope.launch {
            try {
                val response = api.lastSeries(api_key)
                if (response.results.isNotEmpty()) {
                    series.value = response.results
                } else {
                    series.value = emptyList()
                }
            } catch (e: Exception) {
                series.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Fonction pour rechercher des séries par mot-clé
    fun searchSeries(searchQuery: String) {
        viewModelScope.launch {
            try {
                val response = api.searchSeries(api_key, searchQuery)
                if (response.results.isNotEmpty()) {
                    series.value = response.results
                } else {
                    series.value = emptyList()
                }
            } catch (e: Exception) {
                series.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Fonction pour récupérer les acteurs populaires
    fun getActeurs() {
        viewModelScope.launch {
            try {
                val response = api.lastActeurs(api_key)
                if (response.results.isNotEmpty()) {
                    acteurs.value = response.results
                } else {
                    acteurs.value = emptyList()
                }
            } catch (e: Exception) {
                acteurs.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Fonction pour rechercher des acteurs par mot-clé
    fun searchActeurs(searchQuery: String) {
        viewModelScope.launch {
            try {
                val response = api.searchActeurs(api_key, searchQuery)
                if (response.results.isNotEmpty()) {
                    acteurs.value = response.results
                } else {
                    acteurs.value = emptyList()
                }
            } catch (e: Exception) {
                acteurs.value = emptyList() // En cas d'erreur
            }
        }
    }

    // Récupérer les détails du film avec les crédits
// Récupérer les détails du film avec les crédits
    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val details = api.getMovieDetails(
                    movieId = movieId,
                    apiKey = api_key,
                    appendToResponse = "credits" // Demande des crédits dans la réponse
                )
                movieDetails.value = details
            } catch (e: Exception) {
                // Gérer l'erreur
                movieDetails.value = null
            }
        }
    }


    // Récupérer les détails de la série
    fun fetchSerieDetails(serieId: Int) {
        viewModelScope.launch {
            try {
                val details = api.getSerieDetails(
                    serieId,
                    api_key,
                    appendToResponse = "credits")
                serieDetails.value = details
            } catch (e: Exception) {
                // Gérer l'erreur
                serieDetails.value = null
            }
        }
    }

    // Récupérer les détails des acteurs
    fun fetchActeurDetails(acteursId: Int) {
        viewModelScope.launch {
            try {
                val details = api.getActeurDetails(acteursId, api_key)
                acteurDetails.value = details
            } catch (e: Exception) {
                // Gérer l'erreur
                acteurDetails.value = null
            }
        }
    }

    fun fetchPlaylist(uneplaylist: MutableStateFlow<Playlist?>): Playlist {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(Playlist::class.java).fromJson(playlistjson)!!
    }

    fun getPlaylist() {
        viewModelScope.launch{
            fetchPlaylist(maplaylist)
        }
    }

}

