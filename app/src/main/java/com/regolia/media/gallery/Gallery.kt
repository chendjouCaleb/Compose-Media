package com.regolia.media.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay

@Composable
fun Gallery(selectedMedia: Media?, galleryState: GalleryState) {
    galleryState.selectedMedia = selectedMedia
    val context = LocalContext.current

    val permissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            galleryState.list()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionRequest.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            permissionRequest.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        delay(300)
    }
    NavHost(navController = galleryState.navController, startDestination = "index") {
        composable("index") {
            GalleryIndex(galleryState)
        }

        composable("albums/{albumId}") {
            val albumId = it.arguments?.getString("albumId")!!.toInt()
            val album = galleryState.albums.find { f -> f.id == albumId }!!

            Log.e("ALBUM", "Id: $albumId ")
            AlbumIndex(album, galleryState)
        }
    }
}