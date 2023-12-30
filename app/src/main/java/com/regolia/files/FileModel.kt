package com.regolia.files

import android.media.ThumbnailUtils
import android.provider.MediaStore
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

const val kbUnit = 1024.0
const val mbUnit = 1024 * 1024.0
const val gbUnit = 1024 * 1024 * 1024.0

fun Double.format(scale: Int) = "%.${scale}f".format(this)

data class FileModel(var file: File) {


    fun lastModifiedDate(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getDefault().toZoneId())
    }


}