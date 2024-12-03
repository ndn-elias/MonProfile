import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
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
                title = { Text("Détails de la Série") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Affichage des détails ou message de chargement
        if (serieDetails != null) {
            val serie = serieDetails!!
            val credits = serie.credits // Récupère les crédits
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
                // Affichage de l'image de la série
                item {
                    val posterUrl = "https://image.tmdb.org/t/p/w500${serie.poster_path}"
                    Image(
                        painter = rememberImagePainter(posterUrl),
                        contentDescription = "Affiche de ${serie.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp) // Plus grande image pour mieux représenter la série
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Titre de la série
                item {
                    Text(
                        text = serie.name,
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
                        text = serie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Affichage des acteurs
                // Affichage des acteurs
                item {
                    if (credits?.cast?.isNotEmpty() == true) {
                        Text(
                            text = "Acteurs :",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        credits.cast.forEach { actor ->
                            Text(
                                text = actor.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Aucun acteur disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                // Affichage de l'équipe (réalisateurs, etc.)
                item {
                    if (credits?.crew?.isNotEmpty() == true) {
                        Text(
                            text = "Équipe :",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        credits.crew.forEach { member ->
                            Text(
                                text = "${member.name} - ${member.job}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Aucune équipe disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
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
