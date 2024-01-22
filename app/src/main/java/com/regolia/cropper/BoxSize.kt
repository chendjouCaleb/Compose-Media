package com.regolia.cropper

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class BoxSize {
    internal var currentWidth by mutableStateOf(0.dp)
    internal var currentHeight by mutableStateOf(0.dp)

    internal var width by mutableStateOf(0.dp)
    internal var height by mutableStateOf(0.dp)
    internal var angle by mutableFloatStateOf(0f)

    var animatedAngle by mutableFloatStateOf(0f)

    fun rotate() {
        if(angle == -360f){
            angle = -90f
        }else {
            angle -= 90
        }
        animatedAngle -= 90f

        if(angle == -90f || angle == -270f){
            currentHeight = width
            currentWidth = ((width.value * width.value) / height.value).dp
        }else{
            currentHeight = height
            currentWidth = width
        }
    }

    var isInit by mutableStateOf(false)
        private set

    fun markAsInit() {
        if(isInit)
            throw IllegalStateException("Cropper is already initialized.")
        isInit = true
    }
}