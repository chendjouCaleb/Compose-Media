package com.regolia.media.gallery

import android.util.Log
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun AlbumGridItem(album: Album, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var thumbnail by remember { mutableStateOf<ImageBitmap?>(null) }

    val size = 256

    LaunchedEffect(Unit) {
        if (album.medias.isNotEmpty()) {
            try {
                delay(500)
                val thumbnailBitmap =
                    context.contentResolver.loadThumbnail(
                        album.medias[0].uri,
                        Size(size, size),
                        null
                    )
                thumbnail = thumbnailBitmap.asImageBitmap()

            } catch (e: Exception) {
                Log.e(
                    "MediaFolderGridItem",
                    "Error on load thumbnail of ${album.medias[0].name}: ",
                    e
                )
            }
        }
    }

    Box(modifier) {
        Box(Modifier.size(size.dp).clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.TopStart) {
            if (thumbnail != null) {
                Image(
                    bitmap = thumbnail!!, contentDescription = "",
                    modifier = Modifier.size(size.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Row(Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = .4f)).padding(8.dp)) {
                Text(
                    album.name(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    album.medias.size.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }
}