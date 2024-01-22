package com.regolia.cropper

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density


@Composable
fun rememberImageCropperState(bitmap: Bitmap, properties: ImageCropperProperties): ImageCropperState {
    val boxSize = remember { BoxSize()}

    val density = LocalDensity.current
    val overlayState = remember { ImageCropperOverlayState(boxSize, density, properties.aspectRatio)}
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
            overlayState.x = 0f
        }

        if(overlayState.y + overlayState.height > boxSize.currentHeight) {
            overlayState.y = 0f
        }
    }

//    fun crop(): Bitmap {
//        val xr = boxSize.width / boxSize.currentWidth
//        val yr = boxSize.height / boxSize.currentHeight
//
//        val x = (overlayState.x * xr).toInt()
//        val y = (overlayState.y * yr).toInt()
//
//
//
//        val width = (overlayState.width * xr).toInt()
//        val height = (overlayState.height * yr).toInt()
//        val matrix = Matrix()
//        matrix.postRotate(boxSize.angle)
//        return Bitmap.createBitmap(bitmap, x, y, width, height, matrix, true)
//    }


    fun crop(): Bitmap {

        val heightRatio = overlayState.height / boxSize.currentHeight
        val widthRatio = overlayState.width / boxSize.currentWidth
        val yRatio = overlayState.y / boxSize.currentHeight
        val xRatio = overlayState.x / boxSize.currentWidth

        val x = (bitmap.width * xRatio).toInt()
        val y = (bitmap.height * yRatio).toInt()

        val width = (bitmap.width * widthRatio).toInt()
        val height = (bitmap.height * heightRatio).toInt()
        val matrix = Matrix()
        matrix.postRotate(boxSize.angle)
        return Bitmap.createBitmap(bitmap, x, y, width, height, matrix, true)
    }
}