package com.example.minutanutricional.ui.minuta

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.minutanutricional.data.Receta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetalleScreen(
    receta: Receta,
    onAtrasClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(receta.nombre) },
                navigationIcon = {
                    IconButton(onClick = onAtrasClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingInterno ->
    }
}

@Preview(showBackground = true)
@Composable
fun RecetaDetallePreviw() {
        Scaffold() {

        }

}