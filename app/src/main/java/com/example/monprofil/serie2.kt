import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.example.monprofil.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerieDetailScreen(serieId: Int, viewModel: MainViewModel = viewModel()) {
    // Appel pour récupérer les détails de la série
    LaunchedEffect(serieId) {
        viewModel.fetchSerieDetails(serieId)
    }

    // Observer l'état des détails de la série
    val serieDetails by viewModel.serieDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de la Série") }
            )
        }
    ) { paddingValues ->
        // Affichage des détails ou message de chargement
        serieDetails?.let { serie ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Affichage de l'image de la série
                item {
                    val posterUrl = "https://image.tmdb.org/t/p/w500${serie.poster_path}"
                    Image(
                        painter = rememberImagePainter(posterUrl),
                        contentDescription = "Affiche de ${serie.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Détails de la série
                item {
                    Text(text = "Titre : ${serie.name}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Synopsis : ${serie.overview}", style = MaterialTheme.typography.bodyMedium)
                }

                // Vous pouvez ajouter d'autres détails supplémentaires si disponibles
            }
        } ?: run {
            Text(text = "Chargement ou série introuvable", modifier = Modifier.padding(16.dp))
        }
    }
}