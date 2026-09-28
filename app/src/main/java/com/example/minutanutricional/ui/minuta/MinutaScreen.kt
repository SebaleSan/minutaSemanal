package com.example.minutanutricional.ui.minuta

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.minutanutricional.data.obtenerRecetas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.minutanutricional.ui.util.cargarBitmapDesdeUrl
import com.example.minutanutricional.ui.util.extraerColorVibrante
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    onRecetaClick: (Receta) -> Unit,
    onPerfilClick: () -> Unit
) {
    val recetas by obtenerRecetas().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta Semanal") },
                actions = {
                    IconButton(onClick = onPerfilClick) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Mi perfil"
                        )
                    }
                }
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
                var colorTarjeta by remember { mutableStateOf(Color.LightGray) }
                val context = LocalContext.current

                LaunchedEffect(receta.imagenUrl) {
                    val bitmap = cargarBitmapDesdeUrl(context, receta.imagenUrl)
                    if (bitmap != null) {
                        colorTarjeta = extraerColorVibrante(bitmap, Color.LightGray)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { onRecetaClick(receta) },
                    colors = CardDefaults.cardColors(containerColor = colorTarjeta)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = receta.imagenUrl,
                            contentDescription = receta.nombre,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = receta.dia, style = MaterialTheme.typography.labelLarge)
                            Text(text = receta.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(text = receta.recomendacion, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}