package ru.itstep

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import com.example.fastcam.R
import ru.itstep.ui.theme.FastCamTheme
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filename = File(
            getExternalFilesDir(null),
            "Pic.jpg")
        setContent {
            FastCamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting(
                        name = filename,
                        onclick = Modifier.clickable {
                            takePhoto(filename)
                        })
                }
            }
        }
    }

    private fun takePhoto(filename: File) {
        val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //takePictureIntent.setType("image/*")
        //takePictureIntent.putExtra(
        //    "android.intent.extras.CAMERA_FACING",
        //    1 ) //android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT)
        val file = FileProvider.getUriForFile(
            this,
            "ru.itstep.fileprovider",
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
    Column {
        Image(
            painter = painterResource(R.drawable.emptyface),
            contentDescription = "Contact profile picture",
            modifier = onclick
        )
        Text(
            text = clicked + "Hello " + name.toString(),
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastCamTheme {
        Greeting(File("Android"), Modifier)
    }
}