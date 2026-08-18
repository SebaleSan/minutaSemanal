package com.example.minutanutricional.data

data class Receta(
    val dia: String,
    val nombre: String,
    val recomendacion: String,
    val ingredientes: List<String>,
    val instrucciones: String

)

val listaRecetas = listOf(
    Receta(
        dia = "Lunes",
        nombre = "Ensalada de quinoa",
        recomendacion = "Rica en proteínas",
        ingredientes = listOf("1 taza de quinoa", "1 pepino", "1 tomate", "Aceite de oliva"),
        instrucciones = "1. Cocinar la quinoa.\n2. Picar el pepino y el tomate.\n3. Mezclar todo y aliñar con aceite."
    ),
    Receta(
        dia = "Martes",
        nombre = "Pollo al horno con verduras",
        recomendacion = "Bajo en grasas",
        ingredientes = listOf("1 pechuga de pollo", "Zanahoria", "Zapallo italiano", "Sal y pimienta"),
        instrucciones = "1. Precalentar el horno.\n2. Cortar las verduras.\n3. Hornear el pollo con las verduras 30 minutos."
    ),
    Receta(
        dia = "Miércoles",
        nombre = "Salmón con arroz integral",
        recomendacion = "Alto en Omega 3",
        ingredientes = listOf(
            "1 filete de salmón",
            "1 taza de arroz integral",
            "1 limón",
            "Sal y pimienta"
        ),
        instrucciones = "1. Cocinar el arroz integral.\n2. Sazonar el salmón con sal, pimienta y jugo de limón.\n3. Cocinar el salmón a la plancha 4 minutos por lado.\n4. Servir el salmón sobre el arroz."
    ),
    Receta(
        dia = "Jueves",
        nombre = "Sopa de lentejas",
        recomendacion = "Rica en fibra y hierro",
        ingredientes = listOf(
            "1 taza de lentejas",
            "1 zanahoria",
            "1/2 cebolla",
            "1 diente de ajo",
            "Caldo de verduras"
        ),
        instrucciones = "1. Picar la cebolla, ajo y zanahoria.\n2. Sofreír las verduras unos minutos.\n3. Agregar las lentejas y el caldo.\n4. Cocinar a fuego medio por 30 minutos."
    ),
    Receta(
        dia = "Viernes",
        nombre = "Wrap de pavo y palta",
        recomendacion = "Balanceado en proteínas y grasas buenas",
        ingredientes = listOf(
            "2 tortillas de trigo",
            "150 g de pechuga de pavo",
            "1 palta",
            "Hojas de lechuga"
        ),
        instrucciones = "1. Calentar las tortillas.\n2. Rellenar con pavo, palta en láminas y lechuga.\n3. Enrollar y servir."
    )

)