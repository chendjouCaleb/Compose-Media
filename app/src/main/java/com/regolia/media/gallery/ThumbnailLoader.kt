package com.regolia.media.gallery

import android.graphics.Bitmap
import android.net.Uri

class ThumbnailLoaderItem(var uri: Uri, onLoad: (thumbnail: Bitmap) -> Unit)

class ThumbnailLoader {
    private val working = false
    var items = arrayListOf<ThumbnailLoaderItem>()

    fun loadThumbnail(uri: Uri, onLoad: (thumbnail: Bitmap) -> Unit) {
        val item = ThumbnailLoaderItem(uri, onLoad)
        items.add(item)
    }

    private fun loadAll() {
        if(working) {
            return
        }

    }
}