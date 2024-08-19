package com.regolia.media

import com.regolia.media.cropper.CropperProperties
import com.regolia.media.cropper.CropperSnapshot
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CropperSnapshotTest {

    @Test
    fun constructor() {
        val snapshot = CropperSnapshot()
        assertEquals(0f, snapshot.height)
        assertEquals(0f, snapshot.width)
        assertEquals(0f, snapshot.x)
        assertEquals(0f, snapshot.y)
    }


    @Test
    fun setSize_shouldChangeWidthAndHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)

        assertEquals(50f, snapshot.width)
        assertEquals(60f, snapshot.height)
    }

    //region drag
    @Test
    fun dragX_withNegativeValue_shouldDecreaseOffsetX() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(-5f)

        assertEquals(5f, snapshot.x)
    }

    @Test
    fun dragX_withNegativeAmountLowerThanPossible_shouldSetXAtZero(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(-11f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun dragX_withPositiveOffset_shouldIncreaseX() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)

        assertEquals(10f, snapshot.x)
    }

    @Test
    fun dragX_withPositiveOffsetUpperThanPossible_shouldSetXAtMaxWith(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(200f)

        assertEquals(50f, snapshot.x)
    }



    @Test
    fun dragY_withNegativeValue_shouldDecreaseOffsetY() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(-5f)

        assertEquals(5f, snapshot.y)
    }

    @Test
    fun dragY_withNegativeAmountLowerThanPossible_shouldSetYAtZero(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(-11f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun dragY_withPositiveOffset_shouldIncreaseY() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)

        assertEquals(10f, snapshot.y)
    }

    @Test
    fun dragY_withPositiveOffsetUpperThanPossible_shouldSetYAtMaxHeight(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(200f)

        assertEquals(40f, snapshot.y)
    }

    //endregion

    //region moveStart
    @Test
    fun moveStart_with_positiveValue_shouldIncreaseX(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        assertEquals(0f, snapshot.x)
        snapshot.moveStart(10f)

        assertEquals(10f, snapshot.x)
    }

    @Test
    fun moveStart_with_positiveValue_shouldDecreaseWidth(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 40f)
        snapshot.moveStart(10f)

        assertEquals(40f, snapshot.width)
    }

    @Test
    fun moveStart_withPositiveValue_UpperThanMinWidth_shouldSetWidthAtMinWidth() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(100f, 60f)
        snapshot.moveStart(100f)
        assertEquals(10f, snapshot.width)
    }

    @Test
    fun moveStart_withPositiveValue_UpperThanMinWidth_shouldSetXWithRemaining() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.moveStart(50f)
        assertEquals(40f, snapshot.x)
    }

    @Test
    fun moveStart_withNegativeValue_shouldDecreaseX(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        assertEquals(0f, snapshot.x)
        snapshot.dragX(20f)
        snapshot.moveStart(-10f)

        assertEquals(10f, snapshot.x)
    }

    @Test
    fun moveStart_withNegativeValue_shouldIncreaseWidth(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(40f, 50f)
        snapshot.dragX(20f)
        snapshot.moveStart(-10f)

        assertEquals(50f, snapshot.width)
    }

    @Test
    fun moveStart_withNegativeValue_LowerThanRemaining_shouldIncreaseWidthWithRemaining() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(20f)
        snapshot.moveStart(-30f)
        assertEquals(70f, snapshot.width)
    }

    @Test
    fun moveStart_withNegativeValue_LowerThanRemaining_shouldSetXAtZero() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.moveStart(-50f)
        assertEquals(0f, snapshot.x)
    }

    //endregion

    //region moveEnd
    @Test
    fun moveEnd_withPositiveValue_shouldIncreaseWidthWithOffsetX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(10f)

        assertEquals(60f, snapshot.width)
    }

    @Test
    fun moveEnd_withPositiveValue_shouldNotChangeX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(10f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun moveEnd_withPositiveValue_upperThanRemaining_shouldIncreaseWidthWithRemainingWidth() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(60f)

        assertEquals(100f, snapshot.width)
    }



    @Test
    fun moveEnd_withNegativeValue_shouldDecreaseWidthWithOffsetX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(-10f)

        assertEquals(40f, snapshot.width)
    }

    @Test
    fun moveEnd_withNegativeValue_shouldNotChangeX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(-10f)

        assertEquals(0f, snapshot.x)
    }



    @Test
    fun moveEnd_withNegativeValue_LowerThanMinWidth_shouldSetWithAtMinWidth() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(-60f)

        assertEquals(10f, snapshot.width)
    }

    @Test
    fun moveEnd_withNegativeValue_LowerThanMinWidth_shouldNotChangeX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveEnd(-60f)

        assertEquals(0f, snapshot.x)
    }

//endregion

    //region moveTop
    @Test
    fun moveTop_with_positiveValue_shouldIncreaseYByOffset(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        assertEquals(0f, snapshot.y)
        snapshot.moveTop(10f)

        assertEquals(10f, snapshot.y)
    }

    @Test
    fun moveTop_with_positiveValue_shouldDecreaseHeightByOffset(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 50f)
        snapshot.moveTop(10f)

        assertEquals(40f, snapshot.height)
    }

    @Test
    fun moveTop_withPositiveValue_UpperThanMinHeight_shouldSetHeightAtMinHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(100f, 60f)
        snapshot.moveTop(100f)
        assertEquals(10f, snapshot.height)
    }

    @Test
    fun moveTop_withPositiveValue_FinalHeightLowerThanMinHeight_shouldSetYWithRemaining() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 50f)
        snapshot.moveTop(50f)
        assertEquals(40f, snapshot.y)
    }

    @Test
    fun moveTop_withNegativeOffset_shouldDecreaseY_byOffset(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        assertEquals(0f, snapshot.y)
        snapshot.dragY(20f)
        snapshot.moveTop(-10f)

        assertEquals(10f, snapshot.y)
    }

    @Test
    fun moveTop_withNegativeOffset_shouldIncreaseHeightByOffset(){
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(40f, 40f)
        snapshot.dragY(20f)
        snapshot.moveTop(-10f)

        assertEquals(50f, snapshot.height)
    }

    @Test
    fun moveTop_withNegativeValue_FinalTopLowerThanZero_shouldIncreaseHeightByRemaining() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(20f)
        snapshot.moveTop(-30f)
        assertEquals(80f, snapshot.height)
    }

    @Test
    fun moveTop_withNegativeValue_FinalTopLowerThanZero_shouldSetTopAtZero() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.moveTop(-50f)
        assertEquals(0f, snapshot.y)
    }
//endregion

    //region moveBottom
    @Test
    fun moveBottom_withPositiveValue_shouldIncreaseHeightByOffsetY() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(10f)

        assertEquals(60f, snapshot.height)
    }

    @Test
    fun moveBottom_withPositiveValue_shouldNotChangeTop() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 60f)
        snapshot.moveBottom(10f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun moveBottom_withPositiveValue_withFinalBottomUpperThanMaxHeight_shouldIncreaseHeightByRemainingHeight() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(50f)

        assertEquals(100f, snapshot.height)
    }



    @Test
    fun moveBottom_withNegativeValue_shouldDecreaseHeightByOffsetY() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(-10f)

        assertEquals(40f, snapshot.height)
    }

    @Test
    fun moveBottom_withNegativeValue_shouldNotChangeTop() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(-10f)

        assertEquals(0f, snapshot.y)
    }



    @Test
    fun moveBottom_withNegativeValue_FinalHeightLowerThanMinHeight_shouldSetHeightAtMinHeight() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(-60f)

        assertEquals(10f, snapshot.height)
    }

    @Test
    fun moveBottom_withNegativeValue_FinalHeightLowerThanMinHeight_shouldNotChangeX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.moveBottom(-60f)

        assertEquals(0f, snapshot.x)
    }

    //endregion

//region expandX
    @Test
    fun expandXWithAspectRatioZero_ShouldNotChangeHeight() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 0f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandX(10f)
        assertEquals(50f, snapshot.height)
    }

    @Test
    fun expandXWithNegativeOffset_ShouldDecreaseWidthByScaleOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandX(-2f)

        assertEquals(48f, snapshot.width)
    }


    @Test
    fun expandXWithNegativeOffset_ShouldIncreaseEndByScaleOffsetHalf() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(10f)
        snapshot.expandX(-2f)

        assertEquals(11f, snapshot.x)
    }


    @Test
    fun expandXWithPositiveOffset_ShouldIncreaseWidthByScaleOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandX(2f)

        assertEquals(52f, snapshot.width)
    }


    @Test
    fun expandXWithPositiveOffset_ShouldDecreaseXByScaleOffsetHalf() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(10f)
        snapshot.expandX(2f)

        assertEquals(9f, snapshot.x)
    }

    @Test
    fun expandXWithPositiveOffset_FinalXOverlap0_ShouldSetXAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandX(10f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun expandXWithPositiveOffset_FinalXOverlap_ShouldSetXAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(2f)
        snapshot.expandX(10f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun expandXWithPositiveOffset_FinalEndOverlap_ShouldIncreaseXByScaleOffsetX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(50f)
        snapshot.expandX(10f)

        assertEquals(40f, snapshot.x)
    }

    @Test
    fun expandXWithPositiveOffset_FinalEndOverlapAndTopLowerThanHalfOffset_ShouldSetXAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(94f, 94f)
        snapshot.dragX(4f)
        snapshot.expandX(6f)


        assertEquals(0f, snapshot.x)
        assertEquals(100f, snapshot.width)
        assertEquals(100f, snapshot.end)
    }
    //endregion
    //region expandY
    @Test
    fun expandYWithAspectRatioZero_ShouldNotChangeHeightAndBottom() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 0f)
        )
        snapshot.expandY(10f)
    }

    @Test
    fun expandYWithNegativeOffset_ShouldDecreaseHeightByScaleOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandY(-2f)

        assertEquals(48f, snapshot.height)
    }


    @Test
    fun expandYWithNegativeOffset_ShouldIncreaseTopByScaleOffsetHalf() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(10f)
        snapshot.expandY(-2f)

        assertEquals(11f, snapshot.y)
    }

//    @Test
//    fun expandYWithNegativeOffset_FinalTop_ShouldSetTopAtZero() {
//        val snapshot = CropperSnapshot(
//            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
//        )
//        snapshot.setSize(50f, 50f)
//        snapshot.dragY(2f)
//        snapshot.expandY(-10f)
//
//        assertEquals(0f, snapshot.y)
//    }
//
//    @Test
//    fun expandYWithNegativeOffset_FinalBottomOverlap_ShouldDecreaseTopByScaleOffsetX() {
//        val snapshot = CropperSnapshot(
//            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
//        )
//        snapshot.setSize(50f, 50f)
//        snapshot.dragY(50f)
//        snapshot.expandY(-10f)
//
//        assertEquals(40f, snapshot.y)
//    }

    @Test
    fun expandYWithPositiveOffset_ShouldIncreaseHeightByScaleOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandY(2f)

        assertEquals(52f, snapshot.height)
    }


    @Test
    fun expandYWithPositiveOffset_ShouldDecreaseTopByScaleOffsetHalf() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(10f)
        snapshot.expandY(2f)

        assertEquals(9f, snapshot.y)
    }

    @Test
    fun expandYWithPositiveOffset_FinalTopOverlap0_ShouldSetTopAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.expandY(10f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun expandYWithPositiveOffset_FinalTopOverlap_ShouldSetTopAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(2f)
        snapshot.expandY(10f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun expandYWithPositiveOffset_FinalBottomOverlap_ShouldIncreaseTopByScaleOffsetX() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(50f)
        snapshot.expandY(10f)

        assertEquals(40f, snapshot.y)
    }

    @Test
    fun expandYWithPositiveOffset_FinalBottomOverlapAndTopLowerThanHalfOffset_ShouldSetTopAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(94f, 94f)
        snapshot.dragY(4f)
        snapshot.expandY(6f)


        assertEquals(0f, snapshot.y)
        assertEquals(100f, snapshot.height)
        assertEquals(100f, snapshot.bottom)
    }
    //endregion

    //region aspectRatio

    @Test
    fun changeAspectRatio_ShouldChangeAspectRatio() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)
        snapshot.changeAspectRatio(1.5f)

        assertEquals(1.5f, snapshot.properties.aspectRatio)
    }

    @Test
    fun changeAspectRatio_ShouldScaleHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(10f, 10f)
        snapshot.changeAspectRatio(2f)

        assertEquals(20f, snapshot.height)
    }

    @Test
    fun changeAspectRatio_withOverflow_ShouldSetHeightAtMax() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(60f, 60f)
        snapshot.changeAspectRatio(2f)

        assertEquals(100f, snapshot.height)
    }

    @Test
    fun changeAspectRatio_withOverflowHeight_ShouldScaleWidthByHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(60f, 60f)
        snapshot.changeAspectRatio(2f)

        assertEquals(50f, snapshot.width)
    }

    //endregion

    //region changeWidth
    @Test
    fun changeWidth_shouldChangeWidth() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 50f)
        snapshot.changeWidth(60f)

        assertEquals(60f, snapshot.width)
    }

    @Test
    fun changeWidth_shouldDecreaseXByHalfOffset() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(10f, 10f)
        snapshot.dragX(20f)
        snapshot.changeWidth(20f)

        assertEquals(15f, snapshot.x)
    }

    @Test
    fun changeWidth_withYOverlap_shouldSetXAtZero() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(10f, 10f)
        snapshot.dragX(10f)
        snapshot.changeWidth(50f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun changeWidth_FinalEndOverlap_ShouldDecreaseXByOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(50f)
        snapshot.changeWidth(60f)

        assertEquals(40f, snapshot.x)
    }

    @Test
    fun changeWidth_FinalEndOverlap_ShouldSetEndAtWidth() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(50f)
        snapshot.changeWidth(80f)

        assertEquals(100f, snapshot.end)
    }


    @Test
    fun changeWidth_FinalEndOverlapAndTopLowerThanHalfOffset_ShouldSetXAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragX(30f)
        snapshot.changeWidth(100f)


        assertEquals(0f, snapshot.x)
        assertEquals(100f, snapshot.width)
        assertEquals(100f, snapshot.end)
    }

    //endregion

    //region changeHeight
    @Test
    fun changeHeight_shouldChangeHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(60f, 50f)
        snapshot.changeHeight(60f)

        assertEquals(60f, snapshot.height)
    }

    @Test
    fun changeHeight_shouldDecreaseYByHalfOffset() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(10f, 10f)
        snapshot.dragY(20f)
        snapshot.changeHeight(20f)

        assertEquals(15f, snapshot.y)
    }

    @Test
    fun changeHeight_withYOverlap_shouldSetYAtZero() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(10f, 10f)
        snapshot.dragY(10f)
        snapshot.changeHeight(50f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun changeHeight_FinalBottomOverlap_ShouldDecreaseTopByOffset() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(50f)
        snapshot.changeHeight(60f)

        assertEquals(40f, snapshot.y)
    }

    @Test
    fun changeHeight_FinalBottomOverlap_ShouldSetBottomAtHeight() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(50f)
        snapshot.changeHeight(80f)

        assertEquals(100f, snapshot.bottom)
    }


    @Test
    fun changeHeight_FinalBottomOverlapAndTopLowerThanHalfOffset_ShouldSetTopAtZero() {
        val snapshot = CropperSnapshot(
            CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f, aspectRatio = 1f)
        )
        snapshot.setSize(50f, 50f)
        snapshot.dragY(30f)
        snapshot.changeHeight(100f)


        assertEquals(0f, snapshot.y)
        assertEquals(100f, snapshot.height)
        assertEquals(100f, snapshot.bottom)
    }

    //endregion
}