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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@Composable
fun FilmScreen(viewModel: MainViewModel = viewModel(), searchQuery: String, navController: NavController) {
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            viewModel.searchMovies(searchQuery)
        } else {
            viewModel.getMovies()
        }
    }

    // Détecter l'orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val screenPadding = 80.dp


    LazyVerticalGrid(
        columns = if (isLandscape) GridCells.Fixed(4) else GridCells.Fixed(2), // 4 colonnes en paysage, 2 en portrait
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = screenPadding)
            .padding(top = screenPadding)
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (movies.isNotEmpty()) {
            items(movies) { film ->
                FilmCard(film, navController, isLandscape)
            }
        } else {
            item {
                Text(
                    text = "Aucun film trouvé.",
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
@Composable
fun FilmCard(film: AfficheDeFilm, navController: NavController, isLandscape: Boolean) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${film.poster_path}"

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("movie/${film.id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .heightIn(min = if (isLandscape) 250.dp else 350.dp) // Hauteur réduite en paysage
        ) {
            Image(
                painter = rememberImagePainter(posterUrl),
                contentDescription = "Affiche de ${film.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLandscape) 120.dp else 200.dp) // Hauteur réduite en paysage
                    .padding(bottom = 4.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Text(
                text = if (film.title.length > 25) film.title.take(25) + "..." else film.title,
                style = MaterialTheme.typography.titleSmall, // Texte plus petit
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = film.overview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = if (isLandscape) 2 else 4, // Moins de lignes en paysage
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Note: ${film.vote_average}",
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
