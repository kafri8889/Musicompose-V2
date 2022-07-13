package com.anafthdev.musicompose2.feature.music_player_sheet

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import kotlin.time.Duration

sealed interface MusicPlayerSheetAction {
	data class AddToPlaylist(val song: Song, val playlist: Playlist): MusicPlayerSheetAction
	data class SetTimer(val duration: Duration): MusicPlayerSheetAction
}