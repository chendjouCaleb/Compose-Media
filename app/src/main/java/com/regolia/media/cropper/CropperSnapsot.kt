package com.regolia.media.cropper

data class CropperBox(
    val width: Float = 0f,
    val height: Float = 0f,
    val x: Float = 0f,
    val y: Float = 0f
)

data class CropperProperties(
    val width: Float = 256f,
    val height: Float = 256f,
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

    val bottom: Float
        get() {return y + height }

    val end: Float
        get() { return x + width }

    init {
        reset(properties)
    }

    fun reset(properties: CropperProperties) {
        this.properties = properties
        if(properties.aspectRatio != 0f) {
            changeWidth(properties.width)
            changeHeight(properties.width * properties.aspectRatio)
        }else {
            width = properties.width
            height = properties.height
        }

        this.updateParkerSize()
    }

    fun changeAspectRatio(aspectRatio: Float) {
        properties = properties.copy(aspectRatio = aspectRatio)

        var tempHeight = this.width * aspectRatio
        var tempWidth = this.width
        if(tempHeight > properties.height) {
            tempHeight = properties.height
            tempWidth = tempHeight/aspectRatio
        }

        changeHeight(tempHeight)
        changeWidth(tempWidth)
    }

    fun changeWidth(width: Float) {
        assert(width >= properties.minWidth) { "Width($width) should be upper than minWidth(${properties.minWidth})" }
        assert(width <= properties.width) { "Width($width) should be lower than maxWidth(${properties.width})" }
        
        val offsetX = width - this.width
        this.width = width
        if (x - offsetX / 2 > 0f) {
            x -= offsetX / 2
        }else {
            x = 0f
        }

        if (x + width > properties.width) {
            if (x - offsetX / 2 > 0f) {
                x -= offsetX / 2
            }else {
                x = 0f
            }
        }
    }

    fun changeHeight(height: Float) {
        assert(height <= properties.height) { "Height($height) should be lower than maxHeight(${properties.height})" }
        assert(height >= properties.minHeight) { "Height($height) should be upper than minHeight(${properties.minHeight})" }

        val offsetY = height - this.height
        this.height = height
        if (y - offsetY / 2 > 0f) {
            y -= offsetY / 2
        }else {
            y = 0f
        }

        if (y + height > properties.height) {
            if (y - offsetY / 2 > 0f) {
                y -= offsetY / 2
            }else {
                y = 0f
            }
        }

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

    fun expandX(offsetX: Float) {
        changeWidth(width + offsetX)
    }
  


    fun expandY(offsetX: Float) {
        if (properties.aspectRatio == 0f) {
            return
        }
        val offsetY = offsetX * properties.aspectRatio
        if (offsetX < 0f) {
            expandVerticalNegative(offsetY)
        } else {
           expandVerticalPositive(offsetY)
        }
    }

    private fun expandVerticalNegative(offsetY: Float) {
        height += offsetY
        y -= offsetY / 2
    }

    private fun expandVerticalPositive(offsetY: Float) {
        height += offsetY
        if (y - offsetY / 2 > 0f) {
            y -= offsetY / 2
        }else {
            y = 0f
        }

        if (y + height > properties.height) {
            if (y - offsetY / 2 > 0f) {
                y -= offsetY / 2
            }else {
                y = 0f
            }
        }
    }

    fun coerceOffsetX(offsetX: Float): Float {
        if(properties.aspectRatio == 0f) {
            return offsetX
        }
        val offsetY = offsetX * properties.aspectRatio
        if(offsetX > 0f && offsetY > properties.height - height){
            return (properties.height - height) / properties.aspectRatio
        }
        if(offsetX < 0f && -offsetY > height - properties.minHeight){
            return -(height - properties.minHeight) / properties.aspectRatio
        }
        return offsetX
    }

    fun coerceOffsetY(offsetY: Float): Float {
        if(properties.aspectRatio == 0f) {
            return offsetY
        }
        val offsetX = offsetY / properties.aspectRatio
        if(offsetY > 0f && offsetX > properties.width - width){
            return (properties.width - width) * properties.aspectRatio
        }
        if(offsetX < 0f && -offsetY > width - properties.minWidth){
            return -(width - properties.minWidth) * properties.aspectRatio
        }
        return offsetY
    }

    fun moveStart(offsetX: Float) {
        val coercedOffsetX = coerceOffsetX(offsetX)
        if (offsetX < 0) {
            moveStartNegative(coercedOffsetX)
        } else {
            moveStartPositive(coercedOffsetX)
        }
    }

    private fun moveStartNegative(offsetX: Float) {
        var finalOffsetX = offsetX
        if (x + offsetX < 0f) {
            finalOffsetX = 0 - x
        }

        x += finalOffsetX
        width -= finalOffsetX
        expandY(-finalOffsetX)
    }

    private fun moveStartPositive(offsetX: Float) {
        var finalOffsetX = offsetX
        if (width - offsetX <= properties.minWidth) {
            finalOffsetX = width - properties.minWidth
        }

        x += finalOffsetX
        width -= finalOffsetX
        expandY(-finalOffsetX)
    }

    fun moveEnd(offsetX: Float) {
        if (offsetX < 0f) {
            moveEndNegative(offsetX)
        } else {
            moveEndPositive(offsetX)
        }
    }

    private fun moveEndPositive(offsetX: Float) {
        if (x + width + offsetX > properties.width) {
            width = properties.width - x
        } else {
            width += offsetX
        }
    }

    private fun moveEndNegative(offsetX: Float) {
        if (width + offsetX < properties.minWidth) {
            width = properties.minWidth
        } else {
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
        if (y + offsetY > 0f) {
            y += offsetY
            height -= offsetY
        } else {
            height += y
            y = 0f
        }
    }

    private fun moveTopPositive(offsetY: Float) {
        if (height - offsetY >= properties.minHeight) {
            y += offsetY
            height -= offsetY
        } else {
            y += height - properties.minHeight
            height = properties.minHeight
        }
    }


    fun moveBottom(offsetY: Float) {
        if (offsetY < 0f) {
            moveBottomNegative(offsetY)
        } else {
            moveBottomPositive(offsetY)
        }
    }

    private fun moveBottomPositive(offsetY: Float) {
        if (y + height + offsetY > properties.height) {
            height = properties.height - y
        } else {
            height += offsetY
        }
    }

    private fun moveBottomNegative(offsetY: Float) {
        if (height + offsetY < properties.minHeight) {
            height = properties.minHeight
        } else {
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

    private fun dragXPositive(offsetX: Float) {
        assert(offsetX >= 0f) { "Expect positive or 0 offsetX" }
        if (x + offsetX + width < properties.width) {
            x += offsetX
        } else {
            x = properties.width - width
        }
    }

    private fun dragXNegative(offsetX: Float) {
        assert(offsetX < 0f) { "Expect negative or 0 offsetX" }
        if (-offsetX > x) {
            x = 0f
        } else {
            x += offsetX
        }
    }


    private fun dragYPositive(offsetY: Float) {
        assert(offsetY >= 0f) { "Eypect positive or 0 offsetY" }
        if (y + offsetY + height < properties.height) {
            y += offsetY
        } else {
            y = properties.height - height
        }
    }

    private fun dragYNegative(offsetY: Float) {
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


    fun setSize(width: Float, height: Float) {
        assert(width >= properties.minWidth) { "Width($width) should be upper than minWidth(${properties.minWidth})" }
        assert(height >= properties.minHeight) { "Height($height) should be upper than minHeight(${properties.minHeight})" }
        assert(width <= properties.width) { "Width($width) should be lower than maxWidth(${properties.width})" }
        assert(height <= properties.height) { "Height($height) should be lower than maxHeight(${properties.height})" }
        this.width = width
        this.height = height
    }


}