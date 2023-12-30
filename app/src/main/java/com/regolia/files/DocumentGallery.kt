package com.regolia.files

import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.regolia.media.utils.formatAsFileSize
import com.regolia.media.utils.getPDFs
import java.io.File
import java.time.format.DateTimeFormatter

@Composable
fun DocumentGallery() {
    var files by remember { mutableStateOf(listOf<FileModel>()) }
    LaunchedEffect(Unit) {
        files = getPDFs(Environment.getExternalStorageDirectory()).map { FileModel(it) }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
        Text(text = "Documents (${files.count()})")
        files.forEach {
            
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                Column(Modifier.weight(1f)) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = it.file.name,
                            modifier = Modifier,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    
                    
                    
                    Spacer(Modifier.height(4.dp))

                    Row(Modifier.fillMaxWidth()) {
                        if(it.file.isDirectory) {
                            Text(text = "", modifier = Modifier.weight(1f))
                        }else {
                            Text(text = it.file.length().formatAsFileSize(),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                            )
                        }

                        Text(it.lastModifiedDate().format(DateTimeFormatter.ofPattern("dd MMM. yyyy")),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                        )
                    }
                }
                
            }
            
        }
    }
}