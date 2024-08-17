package com.regolia.media.cropper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

class ImageCropperOverlayState(
    var snapshot: CropperSnapshot,
    var boxSize: BoxSize, var density: Density, var aspectRatio: Float = 1f
) {
    /**
     * Current width of the cropper overlay.
     */
    var width by mutableFloatStateOf(0f)

    /**
     * Current height of the cropper overlay.
     */
    var height by mutableFloatStateOf(0f)

    /**
     * Current X position of the cropper overlay relatively to cropper box.
     */
    var x by mutableFloatStateOf(0f)

    /**
     * Current Y position of the cropper overlay relatively to cropper box.
     */
    var y by mutableFloatStateOf(0f)

    var markHeight by mutableFloatStateOf(0f)
    var markWidth by mutableFloatStateOf(0f)

    fun setSize(width: Float, height: Float) {

        if (aspectRatio != 0f) {
            var finalWidth = width
            if (width / aspectRatio > boxSize.currentHeight) {
                finalWidth = boxSize.currentHeight
            }
            this.width = finalWidth
            this.height = finalWidth / aspectRatio
        } else {
            this.width = width
            this.height = height
        }
    }


    fun isExpandable(offsetX: Float, offsetY: Float): Boolean {
        val conditionX = width - offsetX > 10f && width - offsetX <= boxSize.currentWidth
        val conditionY = height - offsetY > 10f && height - offsetY <= boxSize.currentHeight
        return conditionX && conditionY
    }


    fun expandStart(offsetX: Float) {
        snapshot.expandStart(offsetX)
        update()
    }


    fun expandTop(offsetY: Float) {
        snapshot.expandTop(offsetY)
        update()
    }


    fun expandEnd(offsetX: Float) {
        snapshot.expandEnd(offsetX)
        update()
    }

    fun expandBottom(offsetY: Float) {
        snapshot.expandBottom(offsetY)
        update()
    }

    fun expandTopStart(offsetX: Float) {
        snapshot.expandTopStart(offsetX)
        update()
    }

    fun expandTopEnd(offsetX: Float) {
        snapshot.expandTopEnd(offsetX)
        update()
    }

    fun expandBottomStart(offsetX: Float) {
        snapshot.expandBottomStart(offsetX)
        update()
    }

    fun expandBottomEnd(offsetX: Float) {
        snapshot.expandBottomEnd(offsetX)
        update()
    }


    fun moveStart(offsetX: Float) {
        snapshot.moveStart(offsetX)
        update()
    }

    fun moveTop(offsetY: Float) {
       snapshot.moveTop(offsetY)
        update()
    }

    fun moveEnd(offsetX: Float) {
        snapshot.moveEnd(offsetX)
        update()
    }


    fun moveBottom(offsetY: Float) {
        snapshot.moveBottom(offsetY)
        update()
    }


    fun drag(offsetX: Float, offsetY: Float) {
        snapshot.drag(offsetX, offsetY)
        update()
    }

//    fun moveTopStart(offsetX: Dp, offsetY: Dp) {
//        moveStart(offsetX)
//        moveTop(offsetY)
//    }

    fun update() {
        x = snapshot.x
        y = snapshot.y
        width = snapshot.width
        height = snapshot.height

        markHeight = snapshot.markHeight
        markWidth = snapshot.markWidth

    }


    fun touchStart(offsetX: Float) {
        if (aspectRatio != 0f) {
            expandStart(offsetX)
        } else {
            moveStart(offsetX)
        }
    }

    fun touchEnd(offsetX: Float) {
        if (aspectRatio != 0f) {
            expandEnd(offsetX)
        } else {
            moveEnd(offsetX)
        }
    }

    fun touchTop(offsetY: Float) {
        if (aspectRatio != 0f) {
            expandTop(offsetY)
        } else {
            moveTop(offsetY)
        }
    }

    fun touchBottom(offsetY: Float) {
        if (aspectRatio != 0f) {
            expandBottom(offsetY)
        } else {
            moveBottom(offsetY)
        }
    }

    fun touchTopStart(offsetX: Float, offsetY: Float) {
        if (aspectRatio != 0f) {
            //val offset = max(offsetX, offsetY)
            expandTopStart(offsetX)
        } else {
            moveStart(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomStart(offsetX: Float, offsetY: Float) {
        if (aspectRatio != 0f) {
            expandBottomStart(offsetX)
        } else {
            moveStart(offsetX)
            moveBottom(offsetY)
        }
    }

    fun touchTopEnd(offsetX: Float, offsetY: Float) {
        if (aspectRatio != 0f) {
            expandTopEnd(offsetX)
        } else {
            moveEnd(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomEnd(offsetX: Float, offsetY: Float) {
        if (aspectRatio != 0f) {
            expandBottomEnd(offsetX)
        } else {
            moveEnd(offsetX)
            moveBottom(offsetY)
        }
    }
}