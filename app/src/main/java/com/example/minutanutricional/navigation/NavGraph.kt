package com.example.minutanutricional.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minutanutricional.ui.login.LoginScreen
import com.example.minutanutricional.ui.minuta.MinutaScreen
import com.example.minutanutricional.ui.minuta.RecetaDetalleScreen
import com.example.minutanutricional.ui.registro.RegistroScreen
import androidx.navigation.toRoute
import com.example.minutanutricional.data.obtenerRecetas
import com.example.minutanutricional.ui.perfil.PerfilScreen
import com.example.minutanutricional.ui.recuperar.RecuperarScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                onLoginExitoso = { navController.navigate(Minuta) },
                onIrARegistro = { navController.navigate(Registro) },
                onIrARecuperar = { navController.navigate(Recuperar) }
            )
        }

        composable<Registro> {
            RegistroScreen(
                onRegistroExitoso = { navController.navigate(Minuta) }
            )
        }

        composable<Minuta> {
            MinutaScreen(
                onRecetaClick = { receta -> navController.navigate(DetalleReceta(receta.id)) },
                onPerfilClick = { navController.navigate(Perfil) }
            )
        }



        composable<DetalleReceta> { backStackEntry ->
            val datos: DetalleReceta = backStackEntry.toRoute()
            val recetas by obtenerRecetas().collectAsState(initial = emptyList())
            val receta = recetas.firstOrNull { it.id == datos.id }

            if (receta != null) {
                RecetaDetalleScreen(
                    receta = receta,
                    onAtrasClick = { navController.popBackStack() }
                )
            }
        }



        composable<Recuperar> {
            RecuperarScreen(
                onEnviarClick = { navController.popBackStack() },
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable<Perfil> {
            PerfilScreen(
                onAtrasClick = { navController.popBackStack() },
                onCuentaEliminada = {
                    navController.navigate(Login) {
                        popUpTo(0) // borra todos los destinos de la pila para que el usuario no pueda volver atras
                    }
                }
            )
        }
    }
}