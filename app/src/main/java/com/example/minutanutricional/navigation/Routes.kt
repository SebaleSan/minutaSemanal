package com.example.minutanutricional.navigation

import com.example.minutanutricional.data.Receta
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Registro

@Serializable
object Recuperar

@Serializable
object Minuta

@Serializable
data class DetalleReceta(val id: Int)