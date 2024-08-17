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
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)

        assertEquals(50f, snapshot.width)
        assertEquals(60f, snapshot.height)
    }

    @Test
    fun dragX_withNegativeValue_shouldDecreaseOffsetX() {
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(-5f)

        assertEquals(5f, snapshot.x)
    }

    @Test
    fun dragX_withNegativeAmountLowerThanPossible_shouldSetXAtZero(){
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(-11f)

        assertEquals(0f, snapshot.x)
    }

    @Test
    fun dragX_withPositiveOffset_shouldIncreaseX() {
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)

        assertEquals(10f, snapshot.x)
    }

    @Test
    fun dragX_withPositiveOffsetUpperThanPossible_shouldSetXAtMaxWith(){
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragX(10f)
        snapshot.dragX(200f)

        assertEquals(50f, snapshot.x)
    }



    @Test
    fun dragY_withNegativeValue_shouldDecreaseOffsetY() {
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(-5f)

        assertEquals(5f, snapshot.y)
    }

    @Test
    fun dragY_withNegativeAmountLowerThanPossible_shouldSetYAtZero(){
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(-11f)

        assertEquals(0f, snapshot.y)
    }

    @Test
    fun dragY_withPositiveOffset_shouldIncreaseY() {
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)

        assertEquals(10f, snapshot.y)
    }

    @Test
    fun dragY_withPositiveOffsetUpperThanPossible_shouldSetYAtMayWith(){
        val snapshot = CropperSnapshot(CropperProperties(100f, 100f))
        snapshot.setSize(50f, 60f)
        snapshot.dragY(10f)
        snapshot.dragY(200f)

        assertEquals(40f, snapshot.y)
    }
}