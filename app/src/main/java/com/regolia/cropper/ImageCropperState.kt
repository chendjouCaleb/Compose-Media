package com.regolia.cropper

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp


@Composable
fun rememberImageCropperState(bitmap: Bitmap, properties: ImageCropperProperties): ImageCropperState {
    val boxSize = remember { BoxSize()}
    val overlayState = remember { ImageCropperOverlayState(boxSize, properties.aspectRatio)}
    val density = LocalDensity.current
    return remember {
        ImageCropperState(boxSize, overlayState, bitmap, properties, density)
    }
}

class ImageCropperState(var boxSize: BoxSize,
                        var overlayState: ImageCropperOverlayState,
                        var bitmap: Bitmap,
                        var properties: ImageCropperProperties,
                        var density: Density
    ) {

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
            overlayState.x = 0.dp
        }

        if(overlayState.y + overlayState.height > boxSize.currentHeight) {
            overlayState.y = 0.dp
        }
    }

    fun crop(): Bitmap {
        val r = boxSize.width / boxSize.currentWidth

        val x = with(density) { (overlayState.x.toPx() * r).toInt() }
        val y = with(density) { (overlayState.y.toPx() * r).toInt() }


        val width = with(density) { (overlayState.width.toPx() * r).toInt() }
        val height = with(density) { (overlayState.height.toPx() * r).toInt() }
        val matrix = Matrix()
        matrix.postRotate(boxSize.angle)
        return Bitmap.createBitmap(bitmap, x, y, width, height, matrix, true)
    }
}