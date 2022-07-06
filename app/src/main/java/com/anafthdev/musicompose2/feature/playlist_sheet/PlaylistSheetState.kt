package com.anafthdev.musicompose2.feature.playlist_sheet

import com.anafthdev.musicompose2.data.model.Playlist

data class PlaylistSheetState(
	val playlist: Playlist = Playlist.default,
	val playlistName: String = ""
)
