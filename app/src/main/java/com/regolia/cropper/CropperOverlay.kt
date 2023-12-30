package com.regolia.cropper

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FluentCropperOverlayState(var parent: FluentImageCropperState, var aspectRatio: Float = 1f) {
    var width by mutableStateOf(0.dp)
    var height by mutableStateOf(0.dp)
    var x by mutableStateOf(0.dp)
    var y by mutableStateOf(0.dp)

    fun setSize(width: Dp, height: Dp) {

        if(aspectRatio != 0f) {
            var finalWidth = width
            if(width / aspectRatio > parent.zoneHeight) {
                finalWidth = parent.zoneHeight
            }
            this.width = finalWidth
            this.height = finalWidth / aspectRatio
        }else {
            this.width = width
            this.height = height
        }
    }

    fun assignWidth(width: Dp) {
        if(aspectRatio != 0f) {
            var finalWidth = width
            if(width / aspectRatio > parent.zoneHeight) {
                finalWidth = parent.zoneHeight
            }
            this.width = finalWidth
            this.height = finalWidth / aspectRatio
        }else {
            this.width = width
        }
    }

    fun markWidth(): Dp {
        if(width > 64.dp) {
            return 32.dp
        }
        return width / 2
    }

    fun markHeight(): Dp {
        if(height > 64.dp) {
            return 32.dp
        }
        return height / 2
    }

    fun moveStart(offsetX: Dp) {
        if (x + offsetX > 0.dp && width - offsetX > 10.dp) {
            x += offsetX
            width -= offsetX

            if(aspectRatio != 0f) {
                height -= offsetX / aspectRatio
                y += (offsetX / aspectRatio)/2
            }
        }
    }

    fun moveTop(offsetY: Dp) {
        if (y + offsetY > 0.dp && height - offsetY > 10.dp) {
            y += offsetY
            height -= offsetY

            if(aspectRatio != 0f) {
                width -= offsetY * aspectRatio
                x += offsetY * aspectRatio / 2
            }
        }
    }

    fun  moveEnd(offsetX: Dp) {
        if (width - offsetX > 10.dp && x + width - offsetX < parent.zoneWidth) {
            width -= offsetX

            if(aspectRatio != 0f) {
                height -= offsetX / aspectRatio
                y += (offsetX / aspectRatio)/2
            }
        }
    }



    fun moveBottom(offsetY: Dp) {
        if (height - offsetY > 10.dp && y + height - offsetY < parent.zoneHeight) {
            height -= offsetY

            if(aspectRatio != 0f) {
                width -= offsetY * aspectRatio
                x += offsetY * aspectRatio / 2
            }
        }
    }

    fun moveTopStart(offsetX: Dp, offsetY: Dp) {
        moveStart(offsetX)
        moveTop(offsetY)
    }

    fun moveTopEnd(offsetX: Dp, offsetY: Dp) {
        moveEnd(offsetX)

        moveTop(offsetY)
    }

    fun moveBottomStart(offsetX: Dp, offsetY: Dp) {
        moveStart(offsetX)
        moveBottom(offsetY)
    }

    fun moveBottomEnd(offsetX: Dp, offsetY: Dp) {
        moveEnd(offsetX)
        moveBottom(offsetY)
    }
}

@Composable
fun FluentCropperOverlay(state: FluentCropperOverlayState) {
    Box(
        Modifier
            .size(state.width, state.height)
            .offset(state.x, state.y)
            .border(2.dp, Color.White)
    ) {
        val color = Color.White.copy(alpha = .8f)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width
            val strokeWidth = 1.dp.toPx()
            drawLine(color, Offset(0f, height / 3), Offset(width, height / 3), strokeWidth)
            drawLine(color, Offset(0f, height * 2 / 3), Offset(width, height * 2 / 3), strokeWidth)

            drawLine(color, Offset(width / 3, 0f), Offset(width / 3, height), strokeWidth)
            drawLine(color, Offset(width * 2 / 3, 0f), Offset(width * 2 / 3, height), strokeWidth)


        }

        Box(
            modifier = Modifier
                .size(width = 16.dp, height = state.height)
                .background(Color.Red.copy(.5f))
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveTopStart(dragAmount.x.toDp(), 0.dp)
                    }
                }
        ) {}


        Box(
            modifier = Modifier
                .size(width = state.width, height = 16.dp)
                .background(Color.Red.copy(.5f))
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveTopStart(0.dp, dragAmount.y.toDp())
                    }
                }
        ) {}


        Box(
            modifier = Modifier
                .size(width = 16.dp, height = state.height)
                .background(Color.Red.copy(.5f))
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveTopEnd(-dragAmount.x.toDp(), 0.dp)
                    }
                }
        ) {}


        Box(
            modifier = Modifier
                .size(width = state.width, height = 16.dp)
                .background(Color.Red.copy(.5f))
                .align(Alignment.BottomStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveBottomEnd(0.dp, -dragAmount.y.toDp())
                    }
                }
        ) {}


        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(Color.Blue.copy(.5f))
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveTopStart(dragAmount.x.toDp(), dragAmount.y.toDp())
                        //Log.e("Drag" ,"$dragAmount")
                    }
                }
        ) {}
        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(Color.Blue.copy(.5f))
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveTopEnd(-dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}
        Box(modifier = Modifier
            .size(width = state.markWidth(), height = state.markHeight())
            .background(Color.Blue.copy(.5f))
            .align(BottomStart)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    state.moveBottomStart(dragAmount.x.toDp(), -dragAmount.y.toDp())

                }
            }) {}
        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(Color.Blue.copy(.5f))
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.moveBottomEnd(-dragAmount.x.toDp(), -dragAmount.y.toDp())
                    }
                }
        ) {}

    }
}