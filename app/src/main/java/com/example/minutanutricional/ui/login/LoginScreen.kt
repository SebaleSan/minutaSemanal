package com.example.minutanutricional.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit,
    onIrARecuperar: () -> Unit
){
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(24.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "Minuta Nutricional",
            modifier = Modifier
                .padding(top = 52.dp, bottom = 36.dp)
        )

        Text(text = "Ingrese para recibir recetas personalizadas",
            modifier = Modifier
                .padding(bottom = 22.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = {correo = it},
            label = { Text("Correo Electronico") },
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

        Button(onClick = { onLoginExitoso() }) { Text("Ingresar") }
        TextButton(onClick = { onIrARecuperar() }) { Text("¿Olvidaste tu contraseña?") }
        TextButton(onClick = { onIrARegistro() }) { Text("¿No tienes cuenta? Regístrate") }

    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginExitoso = { },
        onIrARegistro = { },
        onIrARecuperar = { }
    )
}