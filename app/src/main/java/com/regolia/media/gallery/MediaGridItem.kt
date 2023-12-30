package com.regolia.media.gallery

import android.util.Log
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun MediaGridItem(media: Media, onClick: (media: Media) -> Unit) {
    val context = LocalContext.current
    var thumbnail by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        try {
            delay(500)
            val thumbnailBitmap =
                context.contentResolver.loadThumbnail(media.uri, Size(128, 128), null)
            thumbnail = thumbnailBitmap.asImageBitmap()

        } catch (e: Exception) {
            Log.e("VideoRowItem", "Error on load thumbnailof ${media.name}: ", e)
        }
    }

    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .size(96.dp)
            .clickable { onClick(media) }
            .clip(RoundedCornerShape(2.dp)), contentAlignment = Alignment.TopStart
    ) {
        if (thumbnail != null) {
            Image(
                bitmap = thumbnail!!, contentDescription = "",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}