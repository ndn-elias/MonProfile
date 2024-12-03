// series.kt
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun SerieScreen(viewModel: MainViewModel = viewModel(), searchQuery: String, navController: NavController) {
    val series by viewModel.series.collectAsState()

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchSeries(searchQuery)
        } else {
            viewModel.getSeries()
        }
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val screenPadding = 80.dp

    LazyVerticalGrid(
        columns = if (isLandscape) GridCells.Fixed(4) else GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = screenPadding)
            .padding(top = screenPadding)
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(series) { serie ->
            SerieCard(serie, navController, isLandscape)
        }
    }
}

@Composable
fun SerieCard(serie: Serie, navController: NavController, isLandscape: Boolean) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${serie.poster_path}"

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("serie/${serie.id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .heightIn(min = if (isLandscape) 250.dp else 350.dp)
        ) {
            Image(
                painter = rememberImagePainter(posterUrl),
                contentDescription = "Affiche de ${serie.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLandscape) 120.dp else 200.dp)
                    .padding(bottom = 4.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Text(
                text = serie.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp)
            )

            Text(
                text = serie.overview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = if (isLandscape) 2 else 4,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}
