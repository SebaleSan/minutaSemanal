package com.example.minutanutricional.ui.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import android.content.Context
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

fun extraerColorDominante(bitmap: Bitmap, colorPorDefecto: Color): Color {
    val palette = Palette.from(bitmap).generate()
    val colorArgb = palette.getDominantColor(colorPorDefecto.toArgb())
    return Color(colorArgb)
}

fun extraerColorVibrante(bitmap: Bitmap, colorPorDefecto: Color): Color {
    val palette = Palette.from(bitmap).generate()
    val colorArgb = palette.getVibrantColor(colorPorDefecto.toArgb())
    return Color(colorArgb)
}
suspend fun cargarBitmapDesdeUrl(context: Context, url: String): Bitmap? {
    val loader = context.imageLoader
    val request = ImageRequest.Builder(context)
        .data(url)
        .allowHardware(false)
        .build()

    val resultado = loader.execute(request)
    return if (resultado is SuccessResult) {
        (resultado.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
    } else {
        null
    }
}