package com.regolia.cropper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

class ImageCropperOverlayState(var boxSize: BoxSize, var density: Density, var aspectRatio: Float = 1f) {
    /**
     * Current width of the cropper overlay.
     */
    var width by mutableStateOf(0f)

    /**
     * Current height of the cropper overlay.
     */
    var height by mutableStateOf(0f)

    /**
     * Current X position of the cropper overlay relatively to cropper box.
     */
    var x by mutableStateOf(0f)

    /**
     * Current Y position of the cropper overlay relatively to cropper box.
     */
    var y by mutableStateOf(0f)

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


    fun markWidth(): Dp {
        with(density) {
            if (width.toDp() > 96.dp) {
                return 32.dp
            }
            return width.toDp() / 3 - 1.5.dp
        }
    }

    fun markHeight(): Dp {
        with(density) {
            if (height.toDp() > 96.dp) {
                return 32.dp
            }
            return (height.toDp() / 3) - 1.5.dp
        }
    }

    fun isExpandable(offsetX: Float, offsetY: Float): Boolean {
        val conditionX = width - offsetX > 10f && width - offsetX <= boxSize.currentWidth
        val conditionY = height - offsetY > 10f && height - offsetY <= boxSize.currentHeight
        return conditionX && conditionY
    }

    fun expandX(offsetX: Float, offsetY: Float) {
        width -= offsetX
        height -= offsetY

        var amountY = offsetY / 2
        if(y + height + offsetY >= boxSize.currentHeight) {
            amountY = offsetY
        }
        if(y + amountY < 0f) {
            amountY = y
        }
        y += amountY
    }

    fun expandY(offsetX: Float, offsetY: Float) {
        width -= offsetX
        height -= offsetY

        var amountX = offsetX / 2
        if(x + width - offsetX >= boxSize.currentWidth) {
            amountX = offsetX
        }
        if(x + amountX < 0f) {
            amountX = -x
        }
        x += amountX
    }

    fun expandStart(offsetX: Float){
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0f){
                x += offsetX
            }
        }
    }


    fun expandTop(offsetY: Float){
        val offsetX = offsetY * aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandY(offsetX, offsetY)
            if(y + offsetY > 0f){
                y += offsetY
            }
        }
    }


    fun expandEnd(offsetX: Float){
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX >= boxSize.currentWidth) {
                x -= offsetX
            }
            if(x < 0f){
                x =0f
            }
        }
    }

    fun expandBottom(offsetY: Float){
        val offsetX = -offsetY * aspectRatio
        if(isExpandable(offsetX, -offsetY)) {
            expandY(offsetX, -offsetY)
            if(y + height + offsetY > boxSize.currentHeight) {
                y -= offsetY
            }
        }
    }

    fun expandTopStart(offsetX: Float) {
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0f){
                x += offsetX
            }
        }
    }

    fun expandTopEnd(offsetX: Float) {
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX > boxSize.currentWidth) {
                x -= offsetX
            }

            if(x < 0f){
                x =0f
            }
        }
    }

    fun expandBottomStart(offsetX: Float) {
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0f){
                x += offsetX
            }
        }
    }

    fun expandBottomEnd(offsetX: Float) {
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX > boxSize.currentWidth) {
                x -= offsetX
            }
        }
    }



    fun moveStart(offsetX: Float) {
        if (x + offsetX > 0f && width - offsetX > 10f) {
            x += offsetX
            width -= offsetX
        }
    }

    fun moveTop(offsetY: Float) {
        if (y + offsetY > 0f && height - offsetY > 10f) {
            y += offsetY
            height -= offsetY
        }
    }

    fun moveEnd(offsetX: Float) {
        if (width + offsetX > 10f && x + width + offsetX < boxSize.currentWidth) {
            width += offsetX
        }
    }


    fun moveBottom(offsetY: Float) {
        if (height + offsetY > 10f && y + height + offsetY < boxSize.currentHeight) {
            height += offsetY
        }
    }

    fun dragX(offsetX: Float) {
        if(x + offsetX > 0f && x + offsetX + width < boxSize.currentWidth){
            x += offsetX
        }
    }

    fun dragY(offsetY: Float) {
        if(y + offsetY > 0f && y + offsetY + height < boxSize.currentHeight) {
            y += offsetY
        }
    }

    fun drag(offsetX: Float, offsetY: Float) {
        dragX(offsetX)
        dragY(offsetY)
    }

//    fun moveTopStart(offsetX: Dp, offsetY: Dp) {
//        moveStart(offsetX)
//        moveTop(offsetY)
//    }


    fun touchStart(offsetX: Float){
        if(aspectRatio != 0f) {
            expandStart(offsetX)
        }else{
            moveStart(offsetX)
        }
    }

    fun touchEnd(offsetX: Float){
        if(aspectRatio != 0f) {
            expandEnd(offsetX)
        }else{
            moveEnd(offsetX)
        }
    }

    fun touchTop(offsetY: Float){
        if(aspectRatio != 0f) {
            expandTop(offsetY)
        }else{
            moveTop(offsetY)
        }
    }

    fun touchBottom(offsetY: Float){
        if(aspectRatio != 0f) {
            expandBottom(offsetY)
        }else{
            moveBottom(offsetY)
        }
    }

    fun touchTopStart(offsetX: Float, offsetY: Float) {
        if(aspectRatio != 0f) {
            //val offset = max(offsetX, offsetY)
            expandTopStart(offsetX)
        }else{
            moveStart(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomStart(offsetX: Float, offsetY: Float) {
        if(aspectRatio != 0f) {
            expandBottomStart(offsetX)
        }else{
            moveStart(offsetX)
            moveBottom(offsetY)
        }
    }

    fun touchTopEnd(offsetX: Float, offsetY: Float) {
        if(aspectRatio != 0f) {
            expandTopEnd(offsetX)
        }else{
            moveEnd(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomEnd(offsetX: Float, offsetY: Float) {
        if(aspectRatio != 0f) {
            expandBottomEnd(offsetX)
        }else{
            moveEnd(offsetX)
            moveBottom(offsetY)
        }
    }
}