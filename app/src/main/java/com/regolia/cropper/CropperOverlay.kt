package com.regolia.cropper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FluentCropperOverlayState(var parent: FluentImageCropperState, var aspectRatio: Float = 1f) {
    var width by mutableStateOf(0.dp)
    var height by mutableStateOf(0.dp)
    var x by mutableStateOf(0.dp)
    var y by mutableStateOf(0.dp)

    fun setSize(width: Dp, height: Dp) {

        if (aspectRatio != 0f) {
            var finalWidth = width
            if (width / aspectRatio > parent.zoneHeight) {
                finalWidth = parent.zoneHeight
            }
            this.width = finalWidth
            this.height = finalWidth / aspectRatio
        } else {
            this.width = width
            this.height = height
        }
    }


    fun markWidth(): Dp {
        if (width > 96.dp) {
            return 32.dp
        }
        return width / 3 - 2.dp
    }

    fun markHeight(): Dp {
        if (height > 96.dp) {
            return 32.dp
        }
        return (height / 3) - 2.dp
    }

    fun moveStart(offsetX: Dp) {
        if (x + offsetX > 0.dp && width - offsetX > 10.dp) {
            x += offsetX
            width -= offsetX

            if (aspectRatio != 0f) {
                height -= offsetX / aspectRatio
                y += (offsetX / aspectRatio) / 2
            }
        }
    }

    fun moveTop(offsetY: Dp) {
        if (y + offsetY > 0.dp && height - offsetY > 10.dp) {
            y += offsetY
            height -= offsetY

            if (aspectRatio != 0f) {
                width -= offsetY * aspectRatio
                x += offsetY * aspectRatio / 2
            }
        }
    }

    fun moveEnd(offsetX: Dp) {
        if (width - offsetX > 10.dp && x + width - offsetX < parent.zoneWidth) {
            width -= offsetX

            if (aspectRatio != 0f) {
                height -= offsetX / aspectRatio
                y += (offsetX / aspectRatio) / 2
            }
        }
    }


    fun moveBottom(offsetY: Dp) {
        if (height - offsetY > 10.dp && y + height - offsetY < parent.zoneHeight) {
            height -= offsetY

            if (aspectRatio != 0f) {
                width -= offsetY * aspectRatio
                x += offsetY * aspectRatio / 2
            }
        }
    }

    fun dragX(offsetX: Dp) {
        if(x + offsetX > 0.dp && x + offsetX + width < parent.zoneWidth){
            x += offsetX
        }
    }

    fun dragY(offsetY: Dp) {
        if(y + offsetY > 0.dp && y + offsetY + height < parent.zoneHeight) {
            y += offsetY
        }
    }

    fun drag(offsetX: Dp, offsetY: Dp) {
        dragX(offsetX)
        dragY(offsetY)
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
    val resizeCornerBgColor = Color.Transparent
    val resizeBgColor = Color.Transparent
    val dragZoneBgColor = Color.Blue.copy(alpha = .5f)
    Box(
        Modifier
            .size(state.width, state.height)
            .offset(state.x, state.y)) {
        val color = Color.White.copy(alpha = .8f)
        val landMarkColor = Color.White
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width
            val strokeWidth = 1.dp.toPx()
            val markStroke = Stroke(2.dp.toPx())
            val markPadding = 2.dp.toPx()
            drawLine(color, Offset(0f, height / 3), Offset(width, height / 3), strokeWidth)
            drawLine(color, Offset(0f, height * 2 / 3), Offset(width, height * 2 / 3), strokeWidth)

            drawLine(color, Offset(width / 3, 0f), Offset(width / 3, height), strokeWidth)
            drawLine(color, Offset(width * 2 / 3, 0f), Offset(width * 2 / 3, height), strokeWidth)
            drawRect(color, Offset.Zero, Size(width, height), style = Stroke(width = strokeWidth))

            drawLine(
                landMarkColor,
                Offset(width / 2 - (state.markWidth() / 2).toPx(), 2.dp.toPx()),
                Offset(width / 2 + (state.markWidth() / 2).toPx(), 2.dp.toPx()),
                2.dp.toPx()
            )

            drawLine(
                landMarkColor,
                Offset(width / 2 - (state.markWidth() / 2).toPx(), height - 2.dp.toPx()),
                Offset(width / 2 + (state.markWidth() / 2).toPx(), height - 2.dp.toPx()),
                2.dp.toPx()
            )

            drawLine(
                landMarkColor,
                Offset(2.dp.toPx(), height / 2 - (state.markHeight() / 2).toPx()),
                Offset(2.dp.toPx(), height / 2 + (state.markHeight() / 2).toPx()),
                2.dp.toPx()
            )

            drawLine(
                landMarkColor,
                Offset(width - 2.dp.toPx(), height / 2 - (state.markHeight() / 2).toPx()),
                Offset(width - 2.dp.toPx(), height / 2 + (state.markHeight() / 2).toPx()),
                2.dp.toPx()
            )


            val topStartMarkPath = Path()
            topStartMarkPath.moveTo(state.markWidth().toPx(), markPadding)
            topStartMarkPath.lineTo(markPadding, markPadding)
            topStartMarkPath.lineTo(markPadding, state.markHeight().toPx())
            drawPath(topStartMarkPath, landMarkColor, style = markStroke)

            val topEndMarkPath = Path()
            topEndMarkPath.moveTo(width - state.markWidth().toPx(), markPadding)
            topEndMarkPath.lineTo(width - markPadding, markPadding)
            topEndMarkPath.lineTo(width - markPadding, state.markHeight().toPx())
            drawPath(topEndMarkPath, landMarkColor, style = markStroke)

            val bottomStartMarkPath = Path()
            bottomStartMarkPath.moveTo(state.markWidth().toPx(), height - markPadding)
            bottomStartMarkPath.lineTo(markPadding, height - markPadding)
            bottomStartMarkPath.lineTo(markPadding, height - state.markHeight().toPx())
            drawPath(bottomStartMarkPath, landMarkColor, style = markStroke)

            val bottomEndMarkPath = Path()
            bottomEndMarkPath.moveTo(width - state.markWidth().toPx(), height - markPadding)
            bottomEndMarkPath.lineTo(width - markPadding, height - markPadding)
            bottomEndMarkPath.lineTo(width - markPadding, height - state.markHeight().toPx())
            drawPath(bottomEndMarkPath, landMarkColor, style = markStroke)

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(resizeCornerBgColor)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.drag(dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}

        Box(
            modifier = Modifier
                .size(width = 16.dp, height = state.height)
                .background(resizeBgColor)
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
                .background(resizeBgColor)
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
                .background(resizeBgColor)
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
                .background(resizeBgColor)
                .align(BottomStart)
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
                .background(resizeCornerBgColor)
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
                .background(resizeCornerBgColor)
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
            .background(resizeCornerBgColor)
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
                .background(resizeCornerBgColor)
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