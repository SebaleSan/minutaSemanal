package com.example.minutanutricional.ui.minuta

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import com.example.minutanutricional.data.Receta
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.minutanutricional.data.obtenerRecetas



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    onRecetaClick: (Receta) -> Unit
) {
    val recetas by obtenerRecetas().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta Semanal") }
            )
        }
    ) { paddingInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingInterno)
                .padding(16.dp)
        ) {
            items(recetas) { receta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { onRecetaClick(receta) }
                ) {
                    Text(text = receta.dia, style = MaterialTheme.typography.labelLarge)
                    Text(text = receta.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(text = receta.recomendacion, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MinutaPreview() {
    MinutaScreen(
        onRecetaClick = { }
    )
}