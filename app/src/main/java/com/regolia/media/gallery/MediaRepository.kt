package com.regolia.media.gallery

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone


data class Media(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Duration,
    val dateModified: LocalDateTime,
    val size: Long,
    val volumeName: String,
    val mediaType: Int,
    val relativePath: String
)

class Album(val relativePath: String, var medias: List<Media>) {
    var id = relativePath.hashCode()
    fun name(): String {
        val paths = relativePath.split('/').filter { it.isNotEmpty() };
        if(paths.isNotEmpty())
            return paths.last()
        return ""
    }
}

class MediaRepository(var context: Context) {
    var medias = listOf<Media>()
    var albums = listOf<Album>()

    val collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)

    val projection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.DATE_MODIFIED,
        MediaStore.Files.FileColumns.DATE_ADDED,
        MediaStore.Files.FileColumns.DURATION,
        MediaStore.Files.FileColumns.MEDIA_TYPE,
        MediaStore.Files.FileColumns.SIZE,
        MediaStore.Files.FileColumns.VOLUME_NAME,
        MediaStore.Files.FileColumns.RELATIVE_PATH,
        MediaStore.Files.FileColumns.DATA
    )

    // Show only images.
    private val mediaTypeColumn = MediaStore.Files.FileColumns.MEDIA_TYPE
    private val selection = "${mediaTypeColumn} = ?"
    private val selectionArgs = arrayOf(
        MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString()
    )

    val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

    fun list(): List<Media> {
        if(medias.isEmpty()) {
            medias = loadMedias()

            val groups = medias.groupBy { it.relativePath  }
            albums = groups.keys.map { key -> Album(key, groups[key]!!) }
        }
        return medias
    }

    fun loadMedias(): List<Media> {
        val query = context.contentResolver.query(
            collection,
            projection,
            selection, selectionArgs,
            // null, null,
            sortOrder
        )


        var medias = listOf<Media>()
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val volumeNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.VOLUME_NAME)
            val mediaTypeColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)
            val pathTypeColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)


            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val volumeName = cursor.getString(volumeNameColumn)
                val mediaType = cursor.getInt(mediaTypeColumn)
                val relativePath = cursor.getString(pathTypeColumn)
                val dateModified = cursor.getLong(dateModifiedColumn)
//                var contentUri =MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                if(mediaType == MediaStore.Files.)
//
//                val contentUri: Uri = ContentUris.withAppendedId(
//                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                    id
//                )
                val contentUri = MediaStore.Files.getContentUri(volumeName, id)

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                val media = Media(
                    id, contentUri, name,
                    Duration.ofMillis(duration.toLong()),
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(dateModified*1000),
                        TimeZone.getDefault().toZoneId()
                    ),
                    size, volumeName, mediaType, relativePath
                )
                medias = medias + media
            }
        }
        val paths = medias.map { it.relativePath }.toHashSet()
        Log.e(this.javaClass.name, paths.joinToString("\n"))
        return medias
    }
}