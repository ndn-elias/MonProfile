package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter



@Composable
fun FilmScreen(viewModel: MainViewModel = viewModel(), searchQuery: String) {
    // Collecter l'état de 'movies' du ViewModel
    val movies by viewModel.movies.collectAsState()

    // Appeler searchMovies si un mot-clé est saisi
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            viewModel.searchMovies(searchQuery) // Effectuer la recherche par mot-clé
        } else {
            viewModel.getMovies() // Si le champ de recherche est vide, récupérer tous les films
        }
    }

    // Modifier pour éviter que la BottomBar coupe les films
    val screenPadding = 80.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 cartes par ligne
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = screenPadding) // Ajoute un padding en bas pour laisser de l'espace à la BottomBar
            .padding(16.dp), // Padding autour de la grille
        contentPadding = PaddingValues(16.dp), // Ajoute de l'espace autour du grid
        verticalArrangement = Arrangement.spacedBy(8.dp), // Espacement vertical entre les cartes
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacement horizontal entre les cartes
    ) {
        // Vérifier si la liste des films est vide
        if (movies.isNotEmpty()) {
            // Afficher les films si la liste n'est pas vide
            items(movies) { film ->
                FilmCard(film)
            }
        } else {
            // Afficher un message si la liste est vide
            item {
                Text(
                    text = "Aucun film trouvé.",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}



@Composable
fun FilmCard(film: AfficheDeFilm, navController: NavController) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${film.poster_path}" // URL pour l'affiche du film

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp) // Augmentation du padding autour de chaque carte
            .clickable { navController.navigate("serie/${film.id}") }, // Navigation avec l'ID de la série
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(8.dp) // Plus d'élévation pour donner un effet "shadow"
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .heightIn(min = 350.dp) // Fixe une hauteur minimum pour éviter la coupe du contenu
        ) {
            // Affichage de l'image du film (affiche)
            Image(
                painter = rememberImagePainter(posterUrl),
                contentDescription = "Affiche de ${film.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Hauteur fixe pour l'image
                    .padding(bottom = 8.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop // Adapter l'image à la taille sans déformer
            )

            // Titre du film centré, limité à 25 caractères avec "..."
            Text(
                text = if (film.title.length > 25) film.title.take(25) + "..." else film.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1, // Limite à 2 lignes
                overflow = TextOverflow.Ellipsis, // Ajoute "..." si le texte dépasse
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally) // Centre horizontalement
            )

            // Ajouter une brève description du film avec gestion du texte
            Text(
                text = film.overview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4, // Limite à 4 lignes
                overflow = TextOverflow.Ellipsis, // Ajoute "..." si le texte dépasse
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 8.dp) // Supprimer le `weight(1f)`
            )

            // Utilisation de Spacer pour pousser la note en bas
            Spacer(modifier = Modifier.weight(1f))

            // Note du film, également centrée et positionnée en bas
            Text(
                text = "Note: ${film.vote_average}",
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally) // Centre horizontalement
            )
        }
    }
}