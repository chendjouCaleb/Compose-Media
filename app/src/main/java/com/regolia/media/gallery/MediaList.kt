package com.regolia.media.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun MediaList(state: GalleryState) {
    Column {
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize =90.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.medias, key={it.id}) { media ->
                MediaGridItem(media,
                    state.selectedMedia != null && state.selectedMedia!!.id == media.id,
                    onClick = {
                        state.onSelect(media)
                    }
                )
            }
        }
    }
}