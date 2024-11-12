import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import com.example.monprofil.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MainViewModel = viewModel()) {
    // Lancer l'appel pour récupérer les détails du film dès que le composant est initialisé
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    // Observer l'état de movieDetails
    val movieDetails by viewModel.movieDetails.collectAsState()

    // Scaffold pour l'organisation de l'interface utilisateur
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails du Film") }
            )
        }
    ) {
        // Affichage des détails ou message de chargement
        movieDetails?.let { movie ->
            Column {
                Text(text = "Titre : ${movie.title}")
                Text(text = "Description : ${movie.overview}")
                // Ajoutez d'autres éléments d'interface pour afficher les informations souhaitées
            }
        } ?: run {
            Text(text = "Chargement ou film introuvable")
        }
    }
}
