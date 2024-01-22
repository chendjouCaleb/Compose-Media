package com.regolia.cropper

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp


@Composable
fun FluentCropperOverlay(state: ImageCropperOverlayState, properties: ImageCropperProperties) {

    // Just for visual debug. Use non transparent color to visualize specific drag zone.
    val resizeCornerBgColor = Color.Transparent
    val resizeBgColor = Color.Transparent
    val dragZoneBgColor = Color.Transparent
    Box(
        Modifier
            .size(state.width, state.height)
            .offset(state.x, state.y)) {
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
                Offset(width / 2 - (state.markWidth() / 2).toPx(), properties.markPadding.toPx()),
                Offset(width / 2 + (state.markWidth() / 2).toPx(), properties.markPadding.toPx()),
                properties.markStrokeWidth.toPx()
            )

            /** Bottom middle mark **/
            drawLine(
                landMarkColor,
                Offset(width / 2 - (state.markWidth() / 2).toPx(), height - properties.markPadding.toPx()),
                Offset(width / 2 + (state.markWidth() / 2).toPx(), height - properties.markPadding.toPx()),
                properties.markStrokeWidth.toPx()
            )

            /** Start middle mark **/
            drawLine(
                landMarkColor,
                Offset(properties.markPadding.toPx(), height / 2 - (state.markHeight() / 2).toPx()),
                Offset(properties.markPadding.toPx(), height / 2 + (state.markHeight() / 2).toPx()),
                properties.markStrokeWidth.toPx()
            )

            /** Start middle mark **/
            drawLine(
                landMarkColor,
                Offset(width - properties.markPadding.toPx(), height / 2 - (state.markHeight() / 2).toPx()),
                Offset(width - properties.markPadding.toPx(), height / 2 + (state.markHeight() / 2).toPx()),
                properties.markStrokeWidth.toPx()
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
                        state.drag(dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}

        /**
         * Start resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = 16.dp, height = state.height)
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        Log.d("Cropper", "Start X: $dragAmount")
                        state.touchStart(dragAmount.x.toDp())
                    }
                }
        ) {}

        /**
         * Top resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = state.width, height = 16.dp)
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTop(dragAmount.y.toDp())
                    }
                }
        ) {}

        /**
         * End resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = 16.dp, height = state.height)
                .background(resizeBgColor)
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchEnd(dragAmount.x.toDp())
                    }
                }
        ) {}

        /**
         * Bottom resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = state.width, height = 16.dp)
                .background(resizeBgColor)
                .align(BottomStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchBottom(dragAmount.y.toDp())
                    }
                }
        ) {}

        /**
         * Top Start resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(resizeBgColor)
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTopStart(dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}

        /**
         * Top end resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(resizeCornerBgColor)
                .align(Alignment.TopEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchTopEnd(dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}

        /**
         * Bottom Start resizer box.
         */
        Box(modifier = Modifier
            .size(width = state.markWidth(), height = state.markHeight())
            .background(resizeCornerBgColor)
            .align(BottomStart)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    state.touchBottomStart(dragAmount.x.toDp(), dragAmount.y.toDp())
                }
            }) {}

        /**
         * Bottom end resizer box.
         */
        Box(
            modifier = Modifier
                .size(width = state.markWidth(), height = state.markHeight())
                .background(resizeCornerBgColor)
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        state.touchBottomEnd(dragAmount.x.toDp(), dragAmount.y.toDp())
                    }
                }
        ) {}


    }
}