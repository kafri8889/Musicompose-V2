package com.anafthdev.musicompose2.feature.musicompose

import com.anafthdev.musicompose2.data.model.Song

sealed interface MusicomposeAction {
	data class Play(val song: Song): MusicomposeAction
	data class SetPlaying(val isPlaying: Boolean): MusicomposeAction
	data class SetFavorite(val isFavorite: Boolean): MusicomposeAction
	data class SetShowBottomMusicPlayer(val isShowed: Boolean): MusicomposeAction
	object PlayLastSongPlayed: MusicomposeAction
}