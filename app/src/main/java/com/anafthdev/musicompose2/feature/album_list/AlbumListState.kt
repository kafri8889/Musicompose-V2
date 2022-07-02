package com.anafthdev.musicompose2.feature.album_list

import com.anafthdev.musicompose2.data.model.Album

data class AlbumListState(
	val albums: List<Album> = emptyList()
)
