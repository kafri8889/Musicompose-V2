package com.anafthdev.musicompose2.feature.song_selector

import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.model.Song

sealed interface SongSelectorAction {
	data class Search(val query: String): SongSelectorAction
	data class AddSong(val song: Song): SongSelectorAction
	data class RemoveSong(val song: Song): SongSelectorAction
	data class GetPlaylist(val playlistID: Int): SongSelectorAction
	data class SetSongSelectorType(val type: SongSelectorType): SongSelectorAction
}