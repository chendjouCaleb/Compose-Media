package com.regolia.media.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


suspend fun getPDFs(directory: File): List<File> {
    return withContext(Dispatchers.IO) {
        directory.listFiles().toList()
    }
}