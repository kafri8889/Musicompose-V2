package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState

fun Song.isDefault(): Boolean {
	return audioID == Song.default.audioID
}

fun Song.isNotDefault(): Boolean {
	return audioID != Song.default.audioID
}

@Composable
fun Song.isSelected(): Boolean {
	val musicomposeState = LocalMusicomposeState.current
	return musicomposeState.currentSongPlayed.audioID == audioID
}

@Composable
fun Song.isPlaying(): Boolean {
	val musicomposeState = LocalMusicomposeState.current
	return isSelected() && musicomposeState.isPlaying
}
