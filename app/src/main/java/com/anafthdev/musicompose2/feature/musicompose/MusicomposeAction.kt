package com.anafthdev.musicompose2.feature.musicompose

import com.anafthdev.musicompose2.data.model.Song

sealed interface MusicomposeAction {
	data class Play(val song: Song): MusicomposeAction
	data class SnapTo(val duration: Long): MusicomposeAction
	data class UpdateSong(val song: Song): MusicomposeAction
	data class SetPlaying(val isPlaying: Boolean): MusicomposeAction
	data class SetShuffle(val isShuffled: Boolean): MusicomposeAction
	data class UpdateQueueSong(val songs: List<Song>): MusicomposeAction
	data class SetShowBottomMusicPlayer(val isShowed: Boolean): MusicomposeAction
	object PlayLastSongPlayed: MusicomposeAction
	object ChangePlaybackMode: MusicomposeAction
	object Backward: MusicomposeAction
	object Previous: MusicomposeAction
	object Forward: MusicomposeAction
	object Next: MusicomposeAction
}