package com.regolia.media.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun GalleryAlbumList(viewModel: GalleryViewModel) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize =180.dp),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(viewModel.albums, key={it.relativePath}) {
            AlbumGridItem(album = it, Modifier.clickable {
                viewModel.navController.navigate("albums/${it.id}")
            })
        }
    }
}