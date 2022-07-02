package com.anafthdev.musicompose2.feature.search

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
import com.anafthdev.musicompose2.data.model.Song

data class SearchState(
	val query: String = "",
	val songs: List<Song> = emptyList(),
	val albums: List<Album> = emptyList(),
	val artists: List<Artist> = emptyList()
)
