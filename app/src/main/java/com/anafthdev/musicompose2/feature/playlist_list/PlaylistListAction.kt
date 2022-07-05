package com.anafthdev.musicompose2.feature.playlist_list

import com.anafthdev.musicompose2.data.model.Playlist

sealed interface PlaylistListAction {
	data class NewPlaylist(val playlist: Playlist): PlaylistListAction
}