package com.example.fastcam

import android.content.Intent
import android.provider.MediaStore
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fastcam.ui.theme.FastCamTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.io.File
import androidx.core.content.FileProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filename = File(getExternalFilesDir(null), "Image.jpg")
        setContent {
            FastCamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        name = filename,
                        onclick=Modifier.clickable {
                            takePhoto(filename)
                        })
                }
            }
        }
    }

    private fun takePhoto(filename: File) {
        val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = FileProvider.getUriForFile(
            this,
            "com.example.fileprovider",
            filename
        )
        takePictureIntent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            file)
        startActivity(
            takePictureIntent
        )
    }
}

@Composable
fun Greeting(name: File, onclick: Modifier, modifier: Modifier = Modifier) {

    var clicked by remember { mutableStateOf("") }
    Column{
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Image(
        painter = painterResource(R.drawable.cat),
        contentDescription = "Contact profile picture",
        modifier = onclick
    )
    }
}

@Preview
@Composable
fun GreetingPreview() {
    FastCamTheme {
        Greeting(File("Android"), Modifier)
    }
}