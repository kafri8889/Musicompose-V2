package com.anafthdev.musicompose2.feature.playlist.playlist

import com.anafthdev.musicompose2.data.model.Playlist

data class PlaylistState(
	val playlist: Playlist = Playlist.justPlayed
)
