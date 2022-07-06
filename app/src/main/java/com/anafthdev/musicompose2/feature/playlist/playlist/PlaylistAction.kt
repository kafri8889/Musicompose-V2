package com.anafthdev.musicompose2.feature.playlist.playlist

import com.anafthdev.musicompose2.data.model.Playlist

sealed interface PlaylistAction {
	data class GetPlaylist(val playlistID: Int): PlaylistAction
	data class UpdatePlaylist(val playlist: Playlist): PlaylistAction
}