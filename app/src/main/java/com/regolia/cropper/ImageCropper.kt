package com.regolia.cropper

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun ImageCropper(state: ImageCropperState, modifier: Modifier = Modifier) {

    val overlayState = state.overlayState
    val properties = state.properties
    val boxSize = state.boxSize

    val density = LocalDensity.current

    Box(modifier) {

        Image(bitmap = state.bitmap.asImageBitmap(), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.2f)
                .onGloballyPositioned { coords ->
                    if (!boxSize.isInit) {

                        boxSize.height = coords.size.height.toFloat()
                        boxSize.currentHeight = boxSize.height

                        boxSize.width =  coords.size.width.toFloat()
                        boxSize.currentWidth = boxSize.width

                        overlayState.setSize(boxSize.currentWidth, boxSize.currentHeight)
                        overlayState.setSize(boxSize.currentWidth, boxSize.currentHeight)

                        boxSize.markAsInit()
                        Log.d("Cropper", "W: ${boxSize.currentWidth};H: ${boxSize.currentHeight}")
                    }
                },
            contentScale = ContentScale.FillWidth)

        val animateAngle by animateFloatAsState(state.boxSize.animatedAngle, label = "")

        Box(
            Modifier
                .align(Alignment.Center)
                .width(with(state.density){boxSize.currentWidth.toDp()})
                .height(with(state.density){boxSize.currentHeight.toDp()})

                //.wrapContentSize()
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin.Center
                    this.rotationZ = animateAngle

                    //this.rotationY = 90f
                }
                .border(1.dp, Color.Transparent)
        ) {

            Image(bitmap = state.bitmap.asImageBitmap(), contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth)
            Box(modifier = Modifier
                .size(with(state.density){boxSize.currentWidth.toDp()},
                    with(state.density){boxSize.currentHeight.toDp()})


                .drawWithContent {
                    clipRect(
                        left = overlayState.x,
                        top = overlayState.y,
                        right = (overlayState.x + overlayState.width),
                        bottom = (overlayState.y + overlayState.height),
                        clipOp = ClipOp.Difference
                    ) {
                        this@drawWithContent.drawContent()
                    }

                }
                .background(properties.clipColor),
            )

            FluentCropperOverlay(overlayState, properties)
        }
    }
}


