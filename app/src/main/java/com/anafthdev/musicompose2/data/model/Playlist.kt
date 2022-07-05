package com.anafthdev.musicompose2.data.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anafthdev.musicompose2.R

@Entity(tableName = "playlist_table")
data class Playlist(
	@PrimaryKey val id: Int,
	@ColumnInfo(name = "icon") @DrawableRes var icon: Int,
	@ColumnInfo(name = "name") var name: String,
	@ColumnInfo(name = "songs") var songs: List<Song>,
	@ColumnInfo(name = "defaultPlaylist") var isDefault: Boolean = false
) {
	
	companion object {
		
		val default = Playlist(
			id = -1,
			icon = R.drawable.ic_music_unknown,
			name = "-",
			songs = listOf(Song.default)
		)
		
		val favorite = Playlist(
			id = 0,
			icon = R.drawable.ic_favorite_image,
			name = "Favorite",
			songs = emptyList(),
			isDefault = true
		)
		
		val justPlayed = Playlist(
			id = 1,
			icon = R.drawable.ic_just_played_image,
			name = "Just played",
			songs = emptyList(),
			isDefault = true
		)
		
	}
	
}