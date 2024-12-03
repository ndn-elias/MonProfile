// personnes.kt
package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun ActeurScreen(viewModel: MainViewModel = viewModel(), searchQuery: String, navController: NavController) {
    val acteurs by viewModel.acteurs.collectAsState()

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchActeurs(searchQuery) // Recherche des acteurs en fonction du mot clé
        } else {
            viewModel.getActeurs() // Charger les acteurs si la recherche est vide
        }
    }
    val screenPadding = 80.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = screenPadding) // Ajoute un padding en bas pour laisser de l'espace à la BottomBar
            .padding(16.dp), // Padding autour de la grille
        contentPadding = PaddingValues(16.dp)
    ) {
        items(acteurs) { acteur ->
            ActeurCard(acteur, navController)
        }
    }
}

@Composable
fun ActeurCard(acteur: Acteur, navController: NavController) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${acteur.profile_path}"

    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp).clickable { navController.navigate("acteur/${acteur.id}") }, // Navigation avec l'ID de l'acteur
            shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),

        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp).fillMaxWidth().heightIn(min = 350.dp)
        ) {
            Image(
                painter = rememberImagePainter(posterUrl),
                contentDescription = "Photo de ${acteur.name}",
                modifier = Modifier.fillMaxWidth().height(200.dp).padding(bottom = 8.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Text(
                text = acteur.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
            )
        }
    }
}