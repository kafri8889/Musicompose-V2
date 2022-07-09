package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController

@Composable
fun BoxScope.BottomMusicPlayerImpl(
	navController: NavController,
	modifier: Modifier = Modifier
) {
	
	val musicomposeState = LocalMusicomposeState.current
	val songController = LocalSongController.current
	
	AnimatedVisibility(
		visible = musicomposeState.isBottomMusicPlayerShowed,
		enter = slideInVertically(
			initialOffsetY = { it }
		),
		exit = slideOutVertically(
			targetOffsetY = { it }
		),
		modifier = modifier
			.navigationBarsPadding()
			.fillMaxWidth()
			.align(Alignment.BottomCenter)
	) {
		BottomMusicPlayer(
			isPlaying = musicomposeState.isPlaying,
			currentSong = musicomposeState.currentSongPlayed,
			currentDuration = musicomposeState.currentDuration,
			onClick = {
				navController.navigate(
					MusicomposeDestination.BottomSheet.MusicPlayer.route
				)
			},
			onFavoriteClicked = {
				songController?.updateSong(
					musicomposeState.currentSongPlayed.copy(
						isFavorite = !musicomposeState.currentSongPlayed.isFavorite
					)
				)
			},
			onPlayPauseClicked = { isPlaying ->
				if (isPlaying) songController?.resume()
				else songController?.pause()
			}
		)
	}
}
