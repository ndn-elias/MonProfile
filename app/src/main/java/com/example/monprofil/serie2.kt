import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
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
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(text = "Titre : ${serie.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Synopsis : ${serie.overview}", style = MaterialTheme.typography.bodyMedium)
                // Ajoutez d'autres éléments d'interface pour afficher les informations souhaitées
            }
        } ?: run {
            Text(text = "Chargement ou série introuvable", modifier = Modifier.padding(16.dp))
        }
    }
}
