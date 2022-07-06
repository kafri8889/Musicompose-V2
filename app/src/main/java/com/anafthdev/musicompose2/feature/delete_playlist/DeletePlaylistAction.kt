package com.anafthdev.musicompose2.feature.delete_playlist

import com.anafthdev.musicompose2.data.model.Playlist

sealed interface DeletePlaylistAction {
	data class GetPlaylist(val playlistID: Int): DeletePlaylistAction
	data class DeletePlaylist(val playlist: Playlist): DeletePlaylistAction
}