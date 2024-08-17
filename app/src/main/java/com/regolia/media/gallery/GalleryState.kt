package com.regolia.media.gallery


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberGalleryState(value: Media? = null, onMediaClick: (meda: Media) -> Unit): GalleryState {
    val context = LocalContext.current
    val navController = rememberNavController()
    return remember { GalleryState(context, navController,  value, onMediaClick) }
}

class GalleryState(
    val context: Context,
    val navController: NavHostController,
    value: Media?,
    val onSelect: (meda: Media) -> Unit,
)  {
    private val mediaRepository = MediaRepository(context)

    var selectedMedia: Media? by mutableStateOf(value)
    var medias by mutableStateOf(listOf<Media>())
    var albums by mutableStateOf(listOf<Album>())

    fun list() {
        medias = mediaRepository.list()
        albums = mediaRepository.albums
    }
}