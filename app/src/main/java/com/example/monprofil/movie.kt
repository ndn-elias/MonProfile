import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
                title = { Text("Détails du Film") }
            )
        }
    ) { paddingValues ->
        // Affichage des détails ou message de chargement
        movieDetails?.let { movie ->
            // Utilisation de LazyColumn pour permettre le défilement
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()  // Assurez-vous qu'il prend toute la hauteur
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Affichage de l'image du film
                item {
                    val posterUrl = "https://image.tmdb.org/t/p/w185${movie.poster_path}"
                    Image(
                        painter = rememberImagePainter(posterUrl),
                        contentDescription = "Affiche de ${movie.title}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)  // Réduit la hauteur de l'image
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Détails du film
                item {
                    Text(text = "Titre : ${movie.title}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Synopsis : ${movie.overview}", style = MaterialTheme.typography.bodyMedium)
                }

                // Vous pouvez ajouter d'autres détails supplémentaires si disponibles
            }
        } ?: run {
            Text(text = "Chargement ou film introuvable", modifier = Modifier.padding(16.dp))
        }
    }
}
