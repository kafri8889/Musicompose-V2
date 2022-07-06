package com.anafthdev.musicompose2.feature.song_selector

import com.anafthdev.musicompose2.data.model.Song

data class SongSelectorState(
	val query: String = "",
	val songs: List<Song> = emptyList(),
	val selectedSong: List<Song> = emptyList()
)
