package com.regolia.cropper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

class ImageCropperOverlayState(var boxSize: BoxSize, var aspectRatio: Float = 1f) {
    /**
     * Current width of the cropper overlay.
     */
    var width by mutableStateOf(0.dp)

    /**
     * Current height of the cropper overlay.
     */
    var height by mutableStateOf(0.dp)

    /**
     * Current X position of the cropper overlay relatively to cropper box.
     */
    var x by mutableStateOf(0.dp)

    /**
     * Current Y position of the cropper overlay relatively to cropper box.
     */
    var y by mutableStateOf(0.dp)

    fun setSize(width: Dp, height: Dp) {

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
        if (width > 96.dp) {
            return 32.dp
        }
        return width / 3 - 1.5.dp
    }

    fun markHeight(): Dp {
        if (height > 96.dp) {
            return 32.dp
        }
        return (height / 3) - 1.5.dp
    }

    fun isExpandable(offsetX: Dp, offsetY: Dp): Boolean {
        val conditionX = width - offsetX > 10.dp && width - offsetX <= boxSize.currentWidth
        val conditionY = height - offsetY > 10.dp && height - offsetY <= boxSize.currentHeight
        return conditionX && conditionY
    }

    fun expandX(offsetX: Dp, offsetY: Dp) {
        width -= offsetX
        height -= offsetY

        var amountY = offsetY / 2
        if(y + height + offsetY >= boxSize.currentHeight) {
            amountY = offsetY
        }
        if(y + amountY < 0.dp) {
            amountY = y
        }
        y += amountY
    }

    fun expandY(offsetX: Dp, offsetY: Dp) {
        width -= offsetX
        height -= offsetY

        var amountX = offsetX / 2
        if(x + width - offsetX >= boxSize.currentWidth) {
            amountX = offsetX
        }
        if(x + amountX < 0.dp) {
            amountX = -x
        }
        x += amountX
    }

    fun expandStart(offsetX: Dp){
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0.dp){
                x += offsetX
            }
        }
    }


    fun expandTop(offsetY: Dp){
        val offsetX = offsetY * aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandY(offsetX, offsetY)
            if(y + offsetY > 0.dp){
                y += offsetY
            }
        }
    }


    fun expandEnd(offsetX: Dp){
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX >= boxSize.currentWidth) {
                x -= offsetX
            }
            if(x < 0.dp){
                x =0.dp
            }
        }
    }

    fun expandBottom(offsetY: Dp){
        val offsetX = -offsetY * aspectRatio
        if(isExpandable(offsetX, -offsetY)) {
            expandY(offsetX, -offsetY)
            if(y + height + offsetY > boxSize.currentHeight) {
                y -= offsetY
            }
        }
    }

    fun expandTopStart(offsetX: Dp) {
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0.dp){
                x += offsetX
            }
        }
    }

    fun expandTopEnd(offsetX: Dp) {
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX > boxSize.currentWidth) {
                x -= offsetX
            }

            if(x < 0.dp){
                x =0.dp
            }
        }
    }

    fun expandBottomStart(offsetX: Dp) {
        val offsetY = offsetX / aspectRatio
        if(isExpandable(offsetX, offsetY)) {
            expandX(offsetX, offsetY)
            if(x + offsetX > 0.dp){
                x += offsetX
            }
        }
    }

    fun expandBottomEnd(offsetX: Dp) {
        val offsetY = -offsetX / aspectRatio
        if(isExpandable(-offsetX, offsetY)) {
            expandX(-offsetX, offsetY)
            if(x + width + offsetX > boxSize.currentWidth) {
                x -= offsetX
            }
        }
    }



    fun moveStart(offsetX: Dp) {
        if (x + offsetX > 0.dp && width - offsetX > 10.dp) {
            x += offsetX
            width -= offsetX
        }
    }

    fun moveTop(offsetY: Dp) {
        if (y + offsetY > 0.dp && height - offsetY > 10.dp) {
            y += offsetY
            height -= offsetY
        }
    }

    fun moveEnd(offsetX: Dp) {
        if (width + offsetX > 10.dp && x + width + offsetX < boxSize.currentWidth) {
            width += offsetX
        }
    }


    fun moveBottom(offsetY: Dp) {
        if (height + offsetY > 10.dp && y + height + offsetY < boxSize.currentHeight) {
            height += offsetY
        }
    }

    fun dragX(offsetX: Dp) {
        if(x + offsetX > 0.dp && x + offsetX + width < boxSize.currentWidth){
            x += offsetX
        }
    }

    fun dragY(offsetY: Dp) {
        if(y + offsetY > 0.dp && y + offsetY + height < boxSize.currentHeight) {
            y += offsetY
        }
    }

    fun drag(offsetX: Dp, offsetY: Dp) {
        dragX(offsetX)
        dragY(offsetY)
    }

//    fun moveTopStart(offsetX: Dp, offsetY: Dp) {
//        moveStart(offsetX)
//        moveTop(offsetY)
//    }

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

    fun touchStart(offsetX: Dp){
        if(aspectRatio != 0f) {
            expandStart(offsetX)
        }else{
            moveStart(offsetX)
        }
    }

    fun touchEnd(offsetX: Dp){
        if(aspectRatio != 0f) {
            expandEnd(offsetX)
        }else{
            moveEnd(offsetX)
        }
    }

    fun touchTop(offsetY: Dp){
        if(aspectRatio != 0f) {
            expandTop(offsetY)
        }else{
            moveTop(offsetY)
        }
    }

    fun touchBottom(offsetY: Dp){
        if(aspectRatio != 0f) {
            expandBottom(offsetY)
        }else{
            moveBottom(offsetY)
        }
    }

    fun touchTopStart(offsetX: Dp, offsetY: Dp) {
        if(aspectRatio != 0f) {
            //val offset = max(offsetX, offsetY)
            expandTopStart(offsetX)
        }else{
            moveStart(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomStart(offsetX: Dp, offsetY: Dp) {
        if(aspectRatio != 0f) {
            val offset = max(offsetX, offsetY)
            expandBottomStart(offsetX)
        }else{
            moveStart(offsetX)
            moveBottom(offsetY)
        }
    }

    fun touchTopEnd(offsetX: Dp, offsetY: Dp) {
        if(aspectRatio != 0f) {
            expandTopEnd(offsetX)
        }else{
            moveEnd(offsetX)
            moveTop(offsetY)
        }
    }

    fun touchBottomEnd(offsetX: Dp, offsetY: Dp) {
        if(aspectRatio != 0f) {
            expandBottomEnd(offsetX)
        }else{
            moveEnd(offsetX)
            moveBottom(offsetY)
        }
    }
}