package com.regolia.media.gallery


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberGalleryViewModel(onSelect: (meda: Media) -> Unit, onDismissRequest: () -> Unit): GalleryViewModel {
    val context = LocalContext.current
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    return viewModel { GalleryViewModel(context, navController, scope, onSelect, onDismissRequest) }
}

class GalleryViewModel(
    val context: Context,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val onSelect: (meda: Media) -> Unit,
    val onDismissRequest: () -> Unit
) : ViewModel() {
    private val mediaRepository = MediaRepository(context)
    var medias by mutableStateOf(listOf<Media>())
    var albums by mutableStateOf(listOf<Album>())

    fun list() {
        medias = mediaRepository.list()
        albums = mediaRepository.albums
    }
}