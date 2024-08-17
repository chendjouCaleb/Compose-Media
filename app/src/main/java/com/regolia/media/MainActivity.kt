package com.regolia.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import coil.compose.AsyncImage
import com.regolia.media.cropper.ImageCropper
import com.regolia.media.cropper.ImageCropperDialog
import com.regolia.media.cropper.ImageCropperProperties
import com.regolia.media.cropper.rememberImageCropperDialog
import com.regolia.media.cropper.rememberImageCropperState
import com.regolia.media.gallery.GalleryPicker
import com.regolia.media.gallery.Media
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
                    Greeting()
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
            val state = rememberImageCropperState(properties)
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

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Blue)) {
                Image(resultBitmap!!.asImageBitmap(), "", contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                    )
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
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    var selectedMedia: Media? by remember { mutableStateOf(null) }

    val pickerState = rememberGalleryPicker()
    val cropperDialogState = rememberImageCropperDialog()

    ImageCropperDialog(state = cropperDialogState) {
        bitmap = it
    }

    GalleryPicker(pickerState, selectedMedia){
        media ->
            selectedMedia = media

            uri = media.uri
            val source = ImageDecoder.createSource(context.contentResolver, media.uri)
            bitmap = ImageDecoder.decodeBitmap(source)

            cropperDialogState.open(bitmap!!)
    }



    Column {
        Text(text = selectedMedia?.id.toString())
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()){
            if(bitmap != null){
                AsyncImage(model = bitmap, contentDescription = "", contentScale = ContentScale.FillWidth)
            }
        }
        Button(onClick = { pickerState.open() }) {
            Text("Ouvrir")
        }
    }
}

