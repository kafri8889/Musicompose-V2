package com.anafthdev.musicompose2.feature.playlist.playlist

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song

data class PlaylistState(
	val playlist: Playlist = Playlist.default,
	val songs: List<Song> = emptyList()
)
