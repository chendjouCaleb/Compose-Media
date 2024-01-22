package com.regolia.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rotate90DegreesCcw
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.regolia.cropper.ImageCropper
import com.regolia.cropper.ImageCropperProperties
import com.regolia.cropper.rememberImageCropperState
import com.regolia.media.gallery.GalleryPicker
import com.regolia.media.gallery.rememberGalleryPicker
import com.regolia.media.ui.theme.MediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CropperImage()
                }
            }
        }
    }
}

@Composable
fun CropperImage() {
    var resultBitmap: Bitmap? by remember { mutableStateOf(null) }
    if(resultBitmap == null){
        Column(
            Modifier
                .fillMaxSize()) {
            val context = LocalContext.current
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bird)
            val properties = ImageCropperProperties(clipColor = Color.Black.copy(alpha = .5f), aspectRatio = 1f)
            val state = rememberImageCropperState(bitmap, properties)
            ImageCropper(state,
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Transparent))

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Annuler")
                }
                IconButton(onClick = { state.rotate() }) {
                    Icon(Icons.Filled.Rotate90DegreesCcw, "")
                }

                TextButton(onClick = { resultBitmap = state.crop() }) {
                    Text(text = "Terminer")
                }
            }
        }
    }else {
        Column(Modifier.fillMaxSize()) {

            Box(Modifier.fillMaxWidth().weight(1f)) {
                Image(resultBitmap!!.asImageBitmap(), "", contentScale = ContentScale.FillWidth)
            }

            Row(Modifier.padding(16.dp)) {
                Button(onClick = { resultBitmap = null }) {
                    Text(text = "Crop")
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val pickerState = rememberGalleryPicker(onChange = { media ->
        uri = media.uri
        bitmap = context.contentResolver.loadThumbnail(media.uri, Size(512, 512), null).asImageBitmap()
    })

    GalleryPicker(pickerState)

}

