package com.regolia.media.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryIndex(viewModel: GalleryViewModel) {
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { viewModel.onDismissRequest() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            },
            title = { Text("Choisir une image") }
        )
    }) {
        Column(Modifier.padding(it)) {
            var tabIndex by remember { mutableIntStateOf(0) }
            val tabs = listOf("Récents", "Gallerie")
            Column(Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text (title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                when(tabIndex) {
                    0 -> MediaList(viewModel)
                    1 -> GalleryAlbumList(viewModel)
                }
            }
        }
    }
}