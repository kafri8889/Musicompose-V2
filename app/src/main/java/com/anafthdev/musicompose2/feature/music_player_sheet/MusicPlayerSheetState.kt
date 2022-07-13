package com.anafthdev.musicompose2.feature.music_player_sheet

import com.anafthdev.musicompose2.data.model.Playlist

data class MusicPlayerSheetState(
	val playlists: List<Playlist> = emptyList(),
	val isTimerActive: Boolean = false
)
