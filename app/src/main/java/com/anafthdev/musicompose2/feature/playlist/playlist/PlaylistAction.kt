package com.anafthdev.musicompose2.feature.playlist.playlist

sealed interface PlaylistAction {
	data class GetPlaylist(val playlistID: Int): PlaylistAction
}