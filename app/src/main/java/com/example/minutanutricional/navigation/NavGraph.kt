package com.example.minutanutricional.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minutanutricional.ui.login.LoginScreen
import com.example.minutanutricional.ui.minuta.MinutaScreen
import com.example.minutanutricional.ui.minuta.RecetaDetalleScreen
import com.example.minutanutricional.ui.registro.RegistroScreen
import androidx.navigation.toRoute
import com.example.minutanutricional.data.listaRecetas
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
                onRecetaClick = { receta -> navController.navigate(DetalleReceta(receta.id)) }
            )
        }

        composable<DetalleReceta> { backStackEntry ->
            val datos: DetalleReceta = backStackEntry.toRoute()
            val receta = listaRecetas.first { it.id == datos.id }

            RecetaDetalleScreen(
                receta = receta,
                onAtrasClick = { navController.popBackStack() }
            )
        }

        composable<Recuperar> {
            RecuperarScreen(
                onEnviarClick = { navController.popBackStack() },
                onVolverClick = { navController.popBackStack() }
            )
        }
    }
}