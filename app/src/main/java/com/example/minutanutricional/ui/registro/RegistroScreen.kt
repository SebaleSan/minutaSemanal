package com.example.minutanutricional.ui.registro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minutanutricional.data.registrarUsuario
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val opcionesDieta = listOf("Normal", "Vegetariana", "Vegana", "Sin gluten")
    var dietaSeleccionada by remember { mutableStateOf(opcionesDieta[0]) }
    var menuExpandido by remember { mutableStateOf(false) }
    val opcionesSexo = listOf("Femenino", "Masculino", "Prefiero no decir")
    var sexoSeleccionado by remember { mutableStateOf(opcionesSexo[0]) }

    var cargando by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .padding(24.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {nombre = it},
            label = {Text("Ingrese su nombre")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = {correo = it},
            label = {Text("Correo Electronico")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {contrasena = it},
            label = {Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(text = "Tipo de Dieta",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp))

        ExposedDropdownMenuBox(
            expanded = menuExpandido,
            onExpandedChange = {menuExpandido = !menuExpandido}
        ) {

            OutlinedTextField(
                value = dietaSeleccionada,
                onValueChange = { },
                readOnly = true,
                label = {Text("Seleccione una opción")},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido)},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = {menuExpandido = false}
            ) {

                opcionesDieta.forEach { opcion ->
                    DropdownMenuItem(
                        text = {Text(opcion)},
                        onClick = {dietaSeleccionada = opcion
                            menuExpandido = false}
                    )
                }
            }

        }

        Text(
            text = "Sexo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 4.dp)
        )

        opcionesSexo.forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                RadioButton(
                    selected = (sexoSeleccionado == opcion),
                    onClick = {sexoSeleccionado = opcion}
                )
                Text (text = opcion)
            }
        }

        if (mensajeError != null) {
            Text(
                text = mensajeError ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                cargando = true
                mensajeError = null
                scope.launch {
                    val resultado = registrarUsuario(
                        correo = correo,
                        contrasena = contrasena,
                        nombre = nombre,
                        dieta = dietaSeleccionada,
                        sexo = sexoSeleccionado
                    )
                    cargando = false
                    resultado
                        .onSuccess { onRegistroExitoso() }
                        .onFailure { error -> mensajeError = error.message }
                }
            },
            enabled = !cargando,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(if (cargando) "Registrando..." else "Registrarse")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    RegistroScreen(
        onRegistroExitoso = { }
    )
}