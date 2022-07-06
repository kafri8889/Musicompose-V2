package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState

@Composable
fun Song.isPlaying(): Boolean {
	val musicomposeState = LocalMusicomposeState.current
	return musicomposeState.currentSongPlayed.audioID == audioID && musicomposeState.isPlaying
}

@Composable
fun Song.isSelected(): Boolean {
	val musicomposeState = LocalMusicomposeState.current
	return musicomposeState.currentSongPlayed.audioID == audioID
}
