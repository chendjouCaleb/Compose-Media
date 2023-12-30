package com.regolia.cropper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class FluentImageCropperState {
    var zoneWidth by mutableStateOf(0.dp)
    var zoneHeight by mutableStateOf(0.dp)

    var isInit by mutableStateOf(false)
}