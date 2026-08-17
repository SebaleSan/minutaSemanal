package com.example.minutanutricional


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minutanutricional.ui.theme.MinutaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinutaTheme {
                MyCompo()


            }
        }
    }
}


@Composable
fun MyCompo(){
    Row(modifier = Modifier.padding(8.dp)) {
        MyImg()
        Bienvenida()

    }
}
@Composable
fun Bienvenida(){
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(text = "Bienvenida")
        Spacer(modifier = Modifier.height(2.dp))
        Text(text="prueba texto 1")
    }
}

@Composable
fun MyImg(){
    Image(
        painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "Imagen de prueba",
        modifier = Modifier.background(Color.Blue).clip(CircleShape).size(42.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MinutaTheme {
        MyCompo()

    }
}