package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActeurDetailScreen(acteurId: Int, viewModel: MainViewModel = viewModel()) {
    // Appel pour récupérer les détails de l'acteur
    LaunchedEffect(acteurId) {
        viewModel.fetchActeurDetails(acteurId)
    }

    // Observer l'état des détails de l'acteur
    val acteurDetails by viewModel.acteurDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de l'Acteur") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        acteurDetails?.let { acteur ->
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
                // Image de l'acteur
                item {
                    val profileUrl = "https://image.tmdb.org/t/p/w500${acteur.profile_path}"
                    Image(
                        painter = rememberImagePainter(profileUrl),
                        contentDescription = "Image de ${acteur.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Nom
                item {
                    Text(
                        text = acteur.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}