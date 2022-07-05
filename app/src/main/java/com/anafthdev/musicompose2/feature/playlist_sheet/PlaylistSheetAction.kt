package com.anafthdev.musicompose2.feature.playlist_sheet

sealed interface PlaylistSheetAction {
	data class ChangePlaylistName(val name: String): PlaylistSheetAction
	object CreatePlaylist: PlaylistSheetAction
}