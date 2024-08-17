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
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun setSize_shouldChangeWidthAndHeight() {
        val snapshot = CropperSnapshot(CropperProperties(width = 100f, height = 100f, minWidth = 10f, minHeight = 10f))
        snapshot.setSize(50f, 60f)

        assertEquals(50f, snapshot.width)
        assertEquals(60f, snapshot.height)
    }

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
}