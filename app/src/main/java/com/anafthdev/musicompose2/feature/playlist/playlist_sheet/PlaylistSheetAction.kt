package com.anafthdev.musicompose2.feature.playlist.playlist_sheet

import com.anafthdev.musicompose2.data.model.Playlist

sealed interface PlaylistSheetAction {
	data class GetPlaylist(val playlistID: Int): PlaylistSheetAction
	data class UpdatePlaylist(val playlist: Playlist): PlaylistSheetAction
	data class ChangePlaylistName(val name: String): PlaylistSheetAction
	data class CreatePlaylist(val name: String): PlaylistSheetAction
}