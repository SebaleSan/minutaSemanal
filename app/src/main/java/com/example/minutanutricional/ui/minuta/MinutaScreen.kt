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
import com.example.minutanutricional.data.listaRecetas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    onRecetaClick: (Receta) -> Unit
) {
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
            items(listaRecetas) { receta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable { onRecetaClick(receta) }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = receta.dia,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = receta.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = receta.recomendacion,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Button(
                            onClick = {onRecetaClick(receta)}
                        ) {
                            Text("Ver")
                        }



                    }
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