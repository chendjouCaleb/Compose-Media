package com.regolia.media.utils

import com.regolia.files.format

const val kbUnit = 1024.0
const val mbUnit = 1024 * 1024.0
const val gbUnit = 1024 * 1024 * 1024.0

fun Double.format(scale: Int) = "%.${scale}f".format(this)

fun Long.formatAsFileSize(): String {
    val length = this.toDouble()
    if (length < com.regolia.files.kbUnit)
        return "${length.format(2)} o"
    else if (length < com.regolia.files.mbUnit)
        return "${(length / com.regolia.files.kbUnit).format(2)} ko"
    else if (length < com.regolia.files.gbUnit)
        return "${(length / com.regolia.files.mbUnit).format(2)} Mo"

    return "${(length / com.regolia.files.gbUnit).format(2)} Gb"
}