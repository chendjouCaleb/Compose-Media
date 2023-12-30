package com.regolia.media.gallery

import android.util.Log
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.regolia.media.utils.formatAsFileSize
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter

@Composable
fun VideoRowItem(media: Media, modifier: Modifier) {
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


    Box(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {

            Box(
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp)), contentAlignment = Alignment.TopStart) {
                if (thumbnail != null) {
                    Image(bitmap = thumbnail!!, contentDescription = "",
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(media.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
               // Text(media.relativePath, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)

                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = media.size.formatAsFileSize(),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(media.dateModified.format(DateTimeFormatter.ofPattern("dd MMM. yyyy")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                    )
                }
            }
        }
    }
}