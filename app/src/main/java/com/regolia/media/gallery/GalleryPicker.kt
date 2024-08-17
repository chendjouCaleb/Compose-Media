package com.regolia.media.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberGalleryPicker(): GalleryPickerState {
    return remember { GalleryPickerState() }
}

class GalleryPickerState() {
    var visible by mutableStateOf(false)
        private set

    fun open() {
        visible = true
    }

    fun close() {
        visible = false
    }
}

@Composable
fun GalleryPicker(state: GalleryPickerState, value: Media?, onChange: (media: Media) -> Unit) {
    val galleryState = rememberGalleryState(value) {
        onChange(it)
        state.close()
    }
    FullScreenDialog(visible = state.visible, onDismissRequest = { state.close() }) {
        Gallery(value, galleryState)
    }
}