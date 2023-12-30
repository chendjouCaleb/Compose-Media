package com.regolia.media

import android.net.Uri
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.regolia.cropper.FluentImageCropper
import com.regolia.files.DocumentGallery
import com.regolia.media.gallery.Gallery
import com.regolia.media.gallery.GalleryPicker
import com.regolia.media.gallery.GalleryViewModel
import com.regolia.media.gallery.rememberGalleryPicker
import com.regolia.media.ui.theme.MediaTheme
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.CropOutline
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.RectCropShape
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty
import com.smarttoolfactory.cropper.settings.CropProperties
import com.smarttoolfactory.cropper.settings.CropType

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
    Box(Modifier.fillMaxSize().padding(32.dp)) {
        FluentImageCropper()
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
    Column {
        if (uri != null) {
            Text(uri!!.toString())
        }
        if (bitmap != null) {
            Image(bitmap!!, "")

            ImageCropper(
                imageBitmap = bitmap!!,
                contentDescription = "",

                cropProperties = CropDefaults.properties(
                    cropType= CropType.Dynamic,
                    cropOutlineProperty = CropOutlineProperty(OutlineType.Rect, RectCropShape(52, "Titke")),
                    handleSize = 1f,
                ),
                onCropStart = { /*TODO*/ },
                onCropSuccess = {

                }
            )
        }
        Button(onClick = { pickerState.open() }) {
            Text("Choisir une image")
        }
    }
}

