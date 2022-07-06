package com.anafthdev.musicompose2.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isPlaying
import com.anafthdev.musicompose2.foundation.extension.isSelected
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.SongItem

@Composable
fun HomeScreen() {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<HomeViewModel>()
	
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		
		items(
			items = musicomposeState.songs,
			key = { song: Song -> song.audioID }
		) { song ->
			SongItem(
				song = song,
				selected = song.isSelected(),
				isMusicPlaying = song.isPlaying(),
				onClick = {
					songController?.play(song)
				},
				onFavoriteClicked = { isFavorite ->
					songController?.updateSong(
						song.copy(
							isFavorite = isFavorite
						)
					)
				}
			)
		}
		
		// BottomMusicPlayer padding
		item {
			Spacer(modifier = Modifier.height(BottomMusicPlayerDefault.Height))
		}
	}
}
