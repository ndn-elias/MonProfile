import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.monprofil.CastMember
import com.example.monprofil.CrewMember
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
            val credits = movie.credits // Récupère les crédits

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

                // Affichage des acteurs stylisé
                item {
                    if (credits?.cast?.isNotEmpty() == true) {
                        Text(
                            text = "Acteurs :",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            items(credits.cast) { actor ->
                                ActorCard(actor = actor)
                            }
                        }
                    } else {
                        Text(
                            text = "Aucun acteur disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                // Affichage de l'équipe stylisée
                item {
                    if (credits?.crew?.isNotEmpty() == true) {
                        Text(
                            text = "Équipe :",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            items(credits.crew) { member ->
                                CrewMemberCard(member = member)
                            }
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

// Composable pour afficher chaque acteur de manière stylisée
@Composable
fun ActorCard(actor: CastMember) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp)
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${actor.profile_path}"
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Profil de ${actor.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = actor.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// Composable pour afficher chaque membre de l'équipe de manière stylisée
@Composable
fun CrewMemberCard(member: CrewMember) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp)
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${member.profile_path}"
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Profil de ${member.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = member.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = member.job,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
