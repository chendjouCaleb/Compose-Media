package com.regolia.media.cropper

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun FluentCropperOverlay(state: ImageCropperOverlayState, properties: ImageCropperProperties) {

    // Just for visual debug. Use non transparent color to visualize specific drag zone.
    val resizeCornerBgColor = Color.Transparent
    val resizeBgColor = Color.Transparent
    val dragZoneBgColor = Color.Transparent
    Box(
        Modifier
            .size(with(state.density){state.width.toDp()}, with(state.density){state.height.toDp()})
            .offset(with(state.density){state.x.toDp()}, with(state.density){state.y.toDp()})) {
        val gridColor = properties.gridColor
        val landMarkColor = properties.markColor
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width
            val gridStrokeWidth = properties.gridStrokeWidth.toPx()
            val markStroke = Stroke(properties.markStrokeWidth.toPx())
            val markPadding = properties.markPadding.toPx()


            drawLine(gridColor, Offset(0f, height / 3), Offset(width, height / 3), gridStrokeWidth)
            drawLine(gridColor, Offset(0f, height * 2 / 3), Offset(width, height * 2 / 3), gridStrokeWidth)

            drawLine(gridColor, Offset(width / 3, 0f), Offset(width / 3, height), gridStrokeWidth)
            drawLine(gridColor, Offset(width * 2 / 3, 0f), Offset(width * 2 / 3, height), gridStrokeWidth)
            drawRect(gridColor, Offset.Zero, Size(width, height), style = Stroke(width = gridStrokeWidth))

            /** Top middle mark **/
            drawLine( landMarkColor,
                Offset(width / 2 - (state.markWidth / 2), properties.markPadding.toPx()),
                Offset(width / 2 + (state.markWidth / 2), properties.markPadding.toPx()),
                properties.markStrokeWidth.toPx()
            )

            /** Bottom middle mark **/
            drawLine(
                landMarkColor,
                Offset(width / 2 - (state.markWidth / 2), height - properties.markPadding.toPx()),
                Offset(width / 2 + (state.markWidth / 2), height - properties.markPadding.toPx()),
                properties.markStrokeWidth.toPx()
            )

            /** Start middle mark **/
            drawLine(
                landMarkColor,
                Offset(properties.markPadding.toPx(), height / 2 - (state.markHeight / 2)),
                Offset(properties.markPadding.toPx(), height / 2 + (state.markHeight / 2)),
                properties.markStrokeWidth.toPx()
            )

            /** Start middle mark **/
            drawLine(
                landMarkColor,
                Offset(width - properties.markPadding.toPx(), height / 2 - (state.markHeight / 2)),
                Offset(width - properties.markPadding.toPx(), height / 2 + (state.markHeight / 2)),
                properties.markStrokeWidth.toPx()
            )


            val topStartMarkPath = Path()
            topStartMarkPath.moveTo(state.markWidth, markPadding)
            topStartMarkPath.lineTo(markPadding, markPadding)
            topStartMarkPath.lineTo(markPadding, state.markHeight)
            drawPath(topStartMarkPath, landMarkColor, style = markStroke)

            val topEndMarkPath = Path()
            topEndMarkPath.moveTo(width - state.markWidth, markPadding)
            topEndMarkPath.lineTo(width - markPadding, markPadding)
            topEndMarkPath.lineTo(width - markPadding, state.markHeight)
            drawPath(topEndMarkPath, landMarkColor, style = markStroke)

            val bottomStartMarkPath = Path()
            bottomStartMarkPath.moveTo(state.markWidth, height - markPadding)
            bottomStartMarkPath.lineTo(markPadding, height - markPadding)
            bottomStartMarkPath.lineTo(markPadding, height - state.markHeight)
            drawPath(bottomStartMarkPath, landMarkColor, style = markStroke)

            val bottomEndMarkPath = Path()
            bottomEndMarkPath.moveTo(width - state.markWidth, height - markPadding)
            bottomEndMarkPath.lineTo(width - markPadding, height - markPadding)
            bottomEndMarkPath.lineTo(width - markPadding, height - state.markHeight)
            drawPath(bottomEndMarkPath, landMarkColor, style = markStroke)

        }

        /**
         * Drag box
         */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dragZoneBgColor)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.drag(dragAmount.x, dragAmount.y)
                    }
                }
        ) {}

        /**
         * Start resizer box.
         */
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(width = 16.dp)
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        Log.d("Cropper", "Start X: $dragAmount")
                        state.touchStart(dragAmount.x)
                    }
                }
        ) {}

        /**
         * Top resizer box.
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height( height = 16.dp)
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTop(dragAmount.y)
                    }
                }
        ) {}

        /**
         * End resizer box.
         */
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(width = 16.dp)
                .background(resizeBgColor)
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchEnd(dragAmount.x)
                    }
                }
        ) {}

        /**
         * Bottom resizer box.
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(resizeBgColor)
                .align(BottomStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchBottom(dragAmount.y)
                    }
                }
        ) {}

        /**
         * Top Start resizer box.
         */
        val density = LocalDensity.current
        val markWidth = with(density) {state.markWidth.toDp()}
        val markHeight = with(density) {state.markHeight.toDp()}
        Box(
            modifier = Modifier
                .size(width = markWidth, height = markHeight)
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTopStart(dragAmount.x, dragAmount.y)
                    }
                }
        ) {}

        /**
         * Top end resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = markWidth, height = markHeight)
                .background(resizeCornerBgColor)
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTopEnd(dragAmount.x, dragAmount.y)
                    }
                }
        ) {}

        /**
         * Bottom Start resizer box.
         */
        Box(modifier = Modifier
            .size(width = markWidth, height = markHeight)
            .background(resizeCornerBgColor)
            .align(BottomStart)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    state.touchBottomStart(dragAmount.x, dragAmount.y)
                }
            }) {}

        /**
         * Bottom end resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = markWidth, height = markHeight)
                .background(resizeCornerBgColor)
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchBottomEnd(dragAmount.x, dragAmount.y)
                    }
                }
        ) {}


    }
}