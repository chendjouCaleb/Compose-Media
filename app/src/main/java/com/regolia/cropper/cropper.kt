package com.regolia.cropper

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.regolia.media.R


@Composable
fun FluentImageCropper() {
    val state = remember { FluentImageCropperState() }
    val overlayState = remember { FluentCropperOverlayState(state)}
    val density = LocalDensity.current
    Box(Modifier.wrapContentSize().graphicsLayer {
        this.transformOrigin = TransformOrigin.Center
        //this.rotationZ = 90f
        //this.rotationY = 90f
    }) {

        Image(painter = painterResource(id = R.drawable.pissenlit), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords ->
                    if(!state.isInit) {
                        state.zoneWidth = with(density) { coords.size.width.toDp() }
                        state.zoneHeight = with(density) { coords.size.height.toDp() }

                        overlayState.setSize(state.zoneWidth, state.zoneHeight)

                        state.isInit = true
                        Log.d("Cropper", "Height: ${state.zoneWidth}")
                    }
                },
        contentScale = ContentScale.FillWidth)
        FluentCropperOverlay(overlayState)
    }
}