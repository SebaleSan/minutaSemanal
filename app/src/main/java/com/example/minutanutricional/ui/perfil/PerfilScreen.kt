package com.example.minutanutricional.ui.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minutanutricional.data.actualizarUsuario
import com.example.minutanutricional.data.eliminarUsuario
import com.example.minutanutricional.data.obtenerUsuarioActual
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onAtrasClick: () -> Unit,
    onCuentaEliminada: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    val opcionesDieta = listOf("Normal", "Vegetariana", "Vegana", "Sin gluten")
    var dietaSeleccionada by remember { mutableStateOf(opcionesDieta[0]) }
    var menuExpandido by remember { mutableStateOf(false) }
    val opcionesSexo = listOf("Femenino", "Masculino", "Prefiero no decir")
    var sexoSeleccionado by remember { mutableStateOf(opcionesSexo[0]) }

    var cargandoDatos by remember { mutableStateOf(true) }
    var guardando by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var mensajeExito by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val resultado = obtenerUsuarioActual()
        resultado.onSuccess { datos ->
            correo = datos.correo
            nombre = datos.nombre
            dietaSeleccionada = datos.dieta
            sexoSeleccionado = datos.sexo
        }
        cargandoDatos = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onAtrasClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingInterno ->
        if (cargandoDatos) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingInterno),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingInterno)
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Correo: $correo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                Text(
                    text = "Tipo de dieta",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = menuExpandido,
                    onExpandedChange = { menuExpandido = !menuExpandido }
                ) {
                    OutlinedTextField(
                        value = dietaSeleccionada,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Selecciona una opción") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        opcionesDieta.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    dietaSeleccionada = opcion
                                    menuExpandido = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Sexo",
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp)
                )

                opcionesSexo.forEach { opcion ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = (sexoSeleccionado == opcion),
                            onClick = { sexoSeleccionado = opcion }
                        )
                        Text(text = opcion)
                    }
                }

                if (mensajeError != null) {
                    Text(
                        text = mensajeError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (mensajeExito != null) {
                    Text(
                        text = mensajeExito ?: "",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        guardando = true
                        mensajeError = null
                        mensajeExito = null
                        scope.launch {
                            val resultado = actualizarUsuario(
                                nombre = nombre,
                                dieta = dietaSeleccionada,
                                sexo = sexoSeleccionado
                            )
                            guardando = false
                            resultado
                                .onSuccess { mensajeExito = "Datos actualizados correctamente" }
                                .onFailure { error -> mensajeError = error.message }
                        }
                    },
                    enabled = !guardando,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                ) {
                    Text(if (guardando) "Guardando..." else "Guardar cambios")
                }

                Button(
                    onClick = { mostrarDialogoEliminar = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                ) {
                    Text("Eliminar mi cuenta")
                }
            }
        }
    }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar cuenta") },
            text = { Text("¿Estás seguro? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogoEliminar = false
                    scope.launch {
                        val resultado = eliminarUsuario()
                        resultado
                            .onSuccess { onCuentaEliminada() }
                            .onFailure { error -> mensajeError = error.message }
                    }
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(
        onAtrasClick = { },
        onCuentaEliminada = { }
    )
}