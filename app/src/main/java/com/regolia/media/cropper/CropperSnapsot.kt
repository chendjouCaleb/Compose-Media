package com.regolia.media.cropper

import androidx.compose.ui.unit.dp

data class CropperBox(
    val width: Float = 0f,
    val height: Float = 0f,
    val x: Float = 0f,
    val y: Float = 0f
)

data class CropperProperties(
    val width: Float = 0f,
    val height: Float = 0f,
    val aspectRatio: Float = 0f,
    val markSize: Float = 96f,
    val minWidth: Float = 256f,
    val minHeight: Float = 256f
)

class CropperSnapshot(properties: CropperProperties = CropperProperties()) {
    var properties = CropperProperties(0f, 0f)
        private set

    val box: CropperBox
        get() {
            return CropperBox(width = width, height = height, x = x, y = y)
        }

    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 0f
    var height: Float = 0f
    var markWidth: Float = 0f
    var markHeight: Float = 0f


    init {
        reset(properties)
    }

    fun reset(properties: CropperProperties) {
        this.properties = properties.copy(aspectRatio = 0f)
        width = properties.width
        height = properties.height
        this.updateParkerSize()
    }


    fun markerWidth(): Float {
        if (properties.width > properties.markSize * 3) {
            return properties.markSize
        }
        return properties.width / 3 - 1.5f
    }

    fun markerHeight(): Float {
        if (properties.height > properties.markSize * 3) {
            return properties.markSize
        }
        return properties.height / 3 - 1.5f
    }

    private fun updateParkerSize() {
        this.markHeight = markerHeight()
        this.markWidth = markerWidth()
    }

    private fun expandX(offsetX: Float, offsetY: Float) {
        width = box.width - offsetX
        height = box.height - offsetY

        var amountY = offsetY / 2
        if (box.y + height + offsetY >= properties.height) {
            amountY = offsetY
        }
        if (box.y + amountY < 0f) {
            amountY = box.y
        }
        y = box.y + amountY
    }

    private fun expandY(offsetX: Float, offsetY: Float) {
        width -= offsetX
        height -= offsetY

        var amountX = offsetX / 2
        if (x + width - offsetX >= properties.width) {
            amountX = offsetX
        }
        if (x + amountX < 0f) {
            amountX = -x
        }
        x += amountX
    }

    fun expandStart(offsetX: Float) {
        val offsetY = offsetX / properties.aspectRatio

        expandX(offsetX, offsetY)
        if (x + offsetX > 0f) {
            x += offsetX
        }
        updateParkerSize()
    }

    fun expandTop(offsetY: Float) {
        val offsetX = offsetY * properties.aspectRatio
        //if(isExpandable(offsetX, offsetY)) {
        expandY(offsetX, offsetY)
        if (y + offsetY > 0f) {
            y += offsetY
        }
        // }
    }


    fun expandEnd(offsetX: Float) {
        val offsetY = -offsetX / properties.aspectRatio
        // if(isExpandable(-offsetX, offsetY)) {
        expandX(-offsetX, offsetY)
        if (x + width + offsetX >= properties.width) {
            x -= offsetX
        }
        if (x < 0f) {
            x = 0f
        }
        // }
    }

    fun expandBottom(offsetY: Float) {
        val offsetX = -offsetY * properties.aspectRatio
        //if(isExpandable(offsetX, -offsetY)) {
        expandY(offsetX, -offsetY)
        if (y + height + offsetY > properties.height) {
            y -= offsetY
        }
        // }
    }

    fun expandTopStart(offsetX: Float) {
        val offsetY = offsetX / properties.aspectRatio
        // if(isExpandable(offsetX, offsetY)) {
        expandX(offsetX, offsetY)
        if (x + offsetX > 0f) {
            x += offsetX
        }
        //}
    }

    fun expandTopEnd(offsetX: Float) {
        val offsetY = -offsetX / properties.aspectRatio
        // if(isExpandable(-offsetX, offsetY)) {
        expandX(-offsetX, offsetY)
        if (x + width + offsetX > properties.width) {
            x -= offsetX
        }

        if (x < 0f) {
            x = 0f
        }
        //}
    }

    fun expandBottomStart(offsetX: Float) {
        val offsetY = offsetX / properties.aspectRatio
        //if(isExpandable(offsetX, offsetY)) {
        expandX(offsetX, offsetY)
        if (x + offsetX > 0f) {
            x += offsetX
        }
        // }
    }

    fun expandBottomEnd(offsetX: Float) {
        val offsetY = -offsetX / properties.aspectRatio
        // if(isExpandable(-offsetX, offsetY)) {
        expandX(-offsetX, offsetY)
        if (x + width + offsetX > properties.width) {
            x -= offsetX
        }
        //  }
    }


    fun moveStart(offsetX: Float) {
        if (offsetX < 0) {
            moveStartNegative(offsetX)
        } else {
           moveStartPositive(offsetX)
        }
    }

    fun moveStartNegative(offsetX: Float) {
        if(x + offsetX > 0f) {
            x += offsetX
            width -= offsetX
        }else {
            width += x
            x = 0f
        }
    }

    fun moveStartPositive(offsetX: Float){
        if(width - offsetX >= properties.minWidth) {
            x += offsetX
            width -= offsetX
        } else {
            x += width - properties.minWidth
            width = properties.minWidth
        }
    }

    fun moveEnd(offsetX: Float) {
        if (offsetX < 0f){
            moveEndNegative(offsetX)
        }else{
            moveEndPositive(offsetX)
        }
    }

    fun moveEndPositive(offsetX: Float) {
        if(x + width + offsetX > properties.width){
            width = properties.width - x
        }else {
            width += offsetX
        }
    }

    fun moveEndNegative(offsetX: Float) {
        if(width + offsetX < properties.minWidth) {
            width = properties.minWidth
        }else {
            width += offsetX
        }
    }

    fun moveTop(offsetY: Float) {
        if (offsetY < 0) {
            moveTopNegative(offsetY)
        } else {
            moveTopPositive(offsetY)
        }
    }

    private fun moveTopNegative(offsetY: Float) {
        if(y + offsetY > 0f) {
            y += offsetY
            height -= offsetY
        }else {
            height += y
            y = 0f
        }
    }

    private fun moveTopPositive(offsetY: Float){
        if(height - offsetY >= properties.minHeight) {
            y += offsetY
            height -= offsetY
        } else {
            y += height - properties.minHeight
            height = properties.minHeight
        }
    }




    fun moveBottom(offsetY: Float) {
        if (offsetY < 0f){
            moveBottomNegative(offsetY)
        }else{
            moveBottomPositive(offsetY)
        }
    }

    fun moveBottomPositive(offsetY: Float) {
        if(y + height + offsetY > properties.height){
            height = properties.height - y
        }else {
            height += offsetY
        }
    }

    fun moveBottomNegative(offsetY: Float) {
        if(height + offsetY < properties.minHeight) {
            height = properties.minHeight
        }else {
            height += offsetY
        }
    }

    fun dragX(offsetX: Float) {
        if (offsetX < 0f) {
            dragXNegative(offsetX)
        } else {
            dragXPositive(offsetX)
        }
    }

    fun dragY(offsetY: Float) {
        if (offsetY < 0f) {
            dragYNegative(offsetY)
        } else {
            dragYPositive(offsetY)
        }
    }

    fun dragXPositive(offsetX: Float) {
        assert(offsetX >= 0f) { "Expect positive or 0 offsetX" }
        if (x + offsetX + width < properties.width) {
            x += offsetX
        } else {
            x = properties.width - width
        }
    }

    fun dragXNegative(offsetX: Float) {
        assert(offsetX < 0f) { "Expect negative or 0 offsetX" }
        if (-offsetX > x) {
            x = 0f
        } else {
            x += offsetX
        }
    }


    fun dragYPositive(offsetY: Float) {
        assert(offsetY >= 0f) { "Eypect positive or 0 offsetY" }
        if (y + offsetY + height < properties.height) {
            y += offsetY
        } else {
            y = properties.height - height
        }
    }

    fun dragYNegative(offsetY: Float) {
        assert(offsetY < 0f) { "Eypect negative or 0 offsetY" }
        if (-offsetY > y) {
            y = 0f
        } else {
            y += offsetY
        }
    }

    fun drag(offsetX: Float, offsetY: Float) {
        dragX(offsetX)
        dragY(offsetY)
    }

    fun changeAspectRatio(aspectRatio: Float) {

    }

    fun setSize(width: Float, height: Float) {
        assert(width > properties.minWidth){"Width($width) should be upper than minWidth(${properties.minWidth})"}
        assert(height > properties.minHeight){"Height($height) should be upper than minHeight(${properties.minHeight})"}
        assert(width <= properties.width){"Width($width) should be lower than maxWidth(${properties.width})"}
        assert(height <= properties.height){"Height($height) should be lower than maxHeight(${properties.height})"}
        this.width = width
        this.height = height
    }


}