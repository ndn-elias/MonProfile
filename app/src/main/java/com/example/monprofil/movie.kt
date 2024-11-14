import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monprofil.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MainViewModel = viewModel()) {
    // Appel pour récupérer les détails de la série
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    // Observer l'état des détails de la série
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
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(text = "Titre : ${movie.title}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Synopsis : ${movie.overview}", style = MaterialTheme.typography.bodyMedium)
                // Ajoutez d'autres éléments d'interface pour afficher les informations souhaitées
            }
        } ?: run {
            Text(text = "Chargement ou film introuvable", modifier = Modifier.padding(16.dp))
        }
    }
}

