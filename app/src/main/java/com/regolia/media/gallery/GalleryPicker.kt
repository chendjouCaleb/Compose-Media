package com.regolia.media.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberGalleryPicker(onChange: (media: Media) -> Unit): GalleryPickerState {
    return remember { GalleryPickerState(onChange) }
}

class GalleryPickerState(var onChange: (media: Media) -> Unit) {
    var visible by mutableStateOf(false)

    fun open() {
        visible = true
    }

    fun close() {
        visible = false
    }
}

@Composable
fun GalleryPicker(state: GalleryPickerState) {
    val viewModel = rememberGalleryViewModel(state.onChange) { state.close() }
    FullScreenDialog(visible = state.visible, onDismissRequest = { state.close() }) {
        Gallery(viewModel)
    }
}