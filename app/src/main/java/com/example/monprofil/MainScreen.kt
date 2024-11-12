package com.example.monprofil

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun MainScreen(navController: NavController) {
    Column {
        Text(text = "Liste des films et séries")
        Button(onClick = { navController.navigate("movie/1") }) {
            Text(text = "Voir Détails Film 1")
        }
        Button(onClick = { navController.navigate("serie/1") }) {
            Text(text = "Voir Détails Série 1")
        }
    }
}

