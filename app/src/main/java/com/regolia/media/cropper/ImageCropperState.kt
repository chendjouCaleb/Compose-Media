package com.regolia.media.cropper

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp


@Composable
fun rememberImageCropperState(properties: ImageCropperProperties): ImageCropperState {
    val boxSize = remember { BoxSize() }


    val density = LocalDensity.current
    val markSize = with(density) { 32.dp.toPx() }
    val cropperProperties = CropperProperties().copy(aspectRatio = properties.aspectRatio, markSize = markSize)
    val snapshot = CropperSnapshot()
    snapshot.reset(cropperProperties)
    val overlayState = remember { ImageCropperOverlayState(snapshot,boxSize, density, properties.aspectRatio) }
    return remember {
        ImageCropperState(snapshot, boxSize, overlayState,  properties, density)
    }
}

class ImageCropperState(
    var snapshot: CropperSnapshot,
    var boxSize: BoxSize,
                        var overlayState: ImageCropperOverlayState,
                        var properties: ImageCropperProperties,
                        var density: Density
    ) {

    var bitmap: Bitmap? by mutableStateOf(null)
    fun changeBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    fun rotate(){
        boxSize.rotate()

        if(properties.aspectRatio != 0f) {
            if(overlayState.width > boxSize.currentWidth){
                overlayState.width = boxSize.currentWidth
                overlayState.height = overlayState.width / properties.aspectRatio
            }
        }else {
            if(overlayState.width > boxSize.currentWidth){
                overlayState.width = boxSize.currentWidth
            }

            if(overlayState.height > boxSize.currentHeight){
                overlayState.height = boxSize.currentHeight
            }
        }

        if(overlayState.x + overlayState.width > boxSize.currentWidth) {
            overlayState.x = 0f
        }

        if(overlayState.y + overlayState.height > boxSize.currentHeight) {
            overlayState.y = 0f
        }
    }


    fun crop(): Bitmap {
        if(bitmap == null){
            throw IllegalStateException("Cannot crop with null bitmap.")
        }

        val heightRatio = snapshot.height / snapshot.properties.height
        val widthRatio = snapshot.width / snapshot.properties.width
        val yRatio = snapshot.y / snapshot.properties.height
        val xRatio = snapshot.x / snapshot.properties.width

        val x = (bitmap!!.width * xRatio).toInt()
        val y = (bitmap!!.height * yRatio).toInt()

        val width = (bitmap!!.width * widthRatio).toInt()
        val height = (bitmap!!.height * heightRatio).toInt()
        val matrix = Matrix()
        matrix.postRotate(boxSize.angle)
        return Bitmap.createBitmap(bitmap!!, x, y, width, height, matrix, true)
    }
}