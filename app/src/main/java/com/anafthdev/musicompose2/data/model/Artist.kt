package com.anafthdev.musicompose2.data.model

data class Artist(
	val id: String,
	val name: String,
	val songs: List<Song>,
	val albums: List<Album>
) {
	companion object {
		val default = Artist(
			id = "-1",
			name = "-",
			songs = emptyList(),
			albums = emptyList()
		)
	}
}
