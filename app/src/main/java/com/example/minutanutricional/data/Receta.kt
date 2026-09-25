package com.example.minutanutricional.data

import kotlinx.serialization.Serializable

@Serializable
data class Receta(
    val id: Int = 0,
    val dia: String = "",
    val nombre: String = "",
    val recomendacion: String = "",
    val ingredientes: List<String> = emptyList(),
    val instrucciones: String = ""
)