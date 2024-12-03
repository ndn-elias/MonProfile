import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.monprofil.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MainViewModel = viewModel()) {
    // Appel pour récupérer les détails du film
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    // Observer l'état des détails du film
    val movieDetails by viewModel.movieDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails du Film") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Affichage des détails ou message de chargement
        if (movieDetails != null) {
            val movie = movieDetails!!
            val screenPadding = 80.dp
            val topscreenPadding = 5.dp

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(bottom = screenPadding)
                    .padding(top = topscreenPadding)
                    .padding(16.dp)
            ) {
                // Affichage de l'image du film
                item {
                    val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                    Image(
                        painter = rememberImagePainter(posterUrl),
                        contentDescription = "Affiche de ${movie.title}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp) // Plus grande image pour mieux représenter le film
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Titre du film
                item {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Synopsis
                item {
                    Text(
                        text = "Synopsis :",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Date de sortie
                item {
                    Text(
                        text = "Date de sortie : ${movie.release_date}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Note moyenne
                item {
                    Text(
                        text = "Note moyenne : ${movie.vote_average}/10 (${movie.vote_count} votes)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        } else {
            // Message de chargement
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
