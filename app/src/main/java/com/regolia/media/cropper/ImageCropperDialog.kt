package com.regolia.media.cropper

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rotate90DegreesCcw
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.regolia.media.gallery.FullScreenDialog


@Composable
fun rememberImageCropperDialog(cropperState: ImageCropperState = rememberImageCropperState(
    ImageCropperProperties()
)
) : ImageCropperDialogState {
    return remember { ImageCropperDialogState(cropperState) }
}

class ImageCropperDialogState(var cropperState: ImageCropperState) {
    var isVisible by mutableStateOf(false)
        private set

    fun open() {
        isVisible = true
    }

    fun open(bitmap: Bitmap) {
        cropperState.changeBitmap(bitmap)
        isVisible = true
    }

    fun close() {
        isVisible = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropperDialog(state: ImageCropperDialogState, onCrop: (bitmap: Bitmap) -> Unit) {
    FullScreenDialog(visible = state.isVisible, onDismissRequest = { state.close() }) {
        Scaffold {
            Column(Modifier.fillMaxSize().padding(it)) {
                ImageCropper(state = state.cropperState, modifier=Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Annuler")
                    }
                    IconButton(onClick = { state.cropperState.rotate() }) {
                        Icon(Icons.Filled.Rotate90DegreesCcw, "")
                    }

                    TextButton(onClick = {
                        val bitmap = state.cropperState.crop()
                        onCrop(bitmap)
                        state.close()
                    }) {
                        Text(text = "Terminer")
                    }
                }
            }
        }
    }
}