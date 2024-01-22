package com.regolia.cropper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageCropperProperties(
    var gridColor: Color = Color.White.copy(alpha = .5f),
    var markColor: Color = Color.White,
    var markStrokeWidth: Dp = 2.dp,
    var markPadding: Dp = 2.dp,
    var gridStrokeWidth: Dp = 1.dp,
    var clipColor: Color = Color.Red.copy(alpha = .8f),

    var aspectRatio: Float = 1f
)