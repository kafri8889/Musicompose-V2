package com.anafthdev.musicompose2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_table")
data class Song(
	@PrimaryKey val audioID: Long,
	@ColumnInfo(name = "displayName") val displayName: String,
	@ColumnInfo(name = "title") val title: String,
	@ColumnInfo(name = "artist") val artist: String,
	@ColumnInfo(name = "album") val album: String,
	@ColumnInfo(name = "albumID") val albumID: String,
	@ColumnInfo(name = "duration") val duration: Long,
	@ColumnInfo(name = "albumPath") val albumPath: String,
	@ColumnInfo(name = "path") val path: String,
	@ColumnInfo(name = "dateAdded") val dateAdded: Long,
	@ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
) {
	companion object {
		val default = Song(
			audioID = -1L,
			displayName = "-",
			title = "-",
			artist = "<unknown>",
			album = "-",
			albumID = "-",
			duration = 0L,
			albumPath = "",
			path = "-",
			dateAdded = 0L,
			isFavorite = false
		)
	}
}
