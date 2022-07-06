package com.anafthdev.musicompose2.feature.delete_playlist

import com.anafthdev.musicompose2.data.model.Playlist

data class DeletePlaylistState(
	val playlist: Playlist = Playlist.default
)
