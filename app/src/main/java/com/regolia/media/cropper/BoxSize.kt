package com.regolia.media.cropper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BoxSize {
    internal var currentWidth by mutableFloatStateOf(0f)
    internal var currentHeight by mutableFloatStateOf(0f)

    internal var width by mutableFloatStateOf(0f)
    internal var height by mutableFloatStateOf(0f)
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
            currentWidth = (width * width) / height
        }else{
            currentHeight = height
            currentWidth = width
        }
    }

    var isInit by mutableStateOf(false)


    fun markAsInit() {
        if(isInit)
            throw IllegalStateException("Cropper is already initialized.")
        isInit = true
    }
}