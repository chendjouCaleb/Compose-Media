package com.regolia.cropper

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun FluentImageCropper(bitmap: Bitmap) {
    val state = remember { FluentImageCropperState(bitmap) }
    val overlayState = remember { FluentCropperOverlayState(state)}
    val density = LocalDensity.current
    Box(
        Modifier
            .wrapContentSize()
            .graphicsLayer {
                this.transformOrigin = TransformOrigin.Center
                //this.rotationZ = 90f
                //this.rotationY = 90f
            }) {

        Image(bitmap = state.bitmap.asImageBitmap(), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords ->
                    if (!state.isInit) {
                        state.zoneWidth = with(density) { coords.size.width.toDp() }
                        state.zoneHeight = with(density) { coords.size.height.toDp() }

                        overlayState.setSize(state.zoneWidth, state.zoneHeight)

                        state.isInit = true
                        Log.d("Cropper", "Height: ${state.zoneWidth}")
                    }
                },
        contentScale = ContentScale.FillWidth)
        Box(modifier = Modifier
            .size(state.zoneWidth, state.zoneHeight)
            .background(Color.Black.copy(alpha = .5f)))
        Image(bitmap.asImageBitmap(), contentDescription = "",
            modifier = Modifier.size(state.zoneWidth, state.zoneHeight)
                .drawWithContent {
                    clipRect(left = overlayState.x.toPx(), top = overlayState.y.toPx(),
                        right = (overlayState.x + overlayState.width).toPx(),
                        bottom = (overlayState.y + overlayState.height).toPx()
                    ) {
                        this@drawWithContent.drawContent()
                    }

                }
                )
        FluentCropperOverlay(overlayState)
    }
}

@Stable
val CropShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) =
        Outline.Rectangle(size.toRect())

    override fun toString(): String = "RectangleShape"
}