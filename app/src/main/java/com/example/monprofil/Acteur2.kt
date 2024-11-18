package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
                title = { Text("Détails de l'Acteur") }
            )
        }
    ) { paddingValues ->
        // Affichage des détails ou message de chargement
        acteurDetails?.let { acteur ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                // Affichage de l'image de l'acteur
                val posterUrl = "https://image.tmdb.org/t/p/w500${acteur.profile_path}"
                Image(
                    painter = rememberImagePainter(posterUrl),
                    contentDescription = "Image de ${acteur.name}",
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )

                // Nom de l'acteur
                Text(
                    text = "Nom : ${acteur.name}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        } ?: run {
            // Message de chargement ou erreur
            Text(text = "Chargement ou acteur introuvable", modifier = Modifier.padding(16.dp))
        }
    }
}

