package com.regolia.cropper

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class FluentImageCropperState(var bitmap : Bitmap) {
    var zoneWidth by mutableStateOf(0.dp)
    var zoneHeight by mutableStateOf(0.dp)

    var isInit by mutableStateOf(false)
}