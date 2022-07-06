package com.anafthdev.musicompose2.feature.artist.artist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.ClearRippleTheme
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.uicomponent.ArtistItem
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.SongItem

@Composable
fun ArtistScreen(
	artistID: String,
	navController: NavController
) {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<ArtistViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	LaunchedEffect(Unit) {
		viewModel.dispatch(
			ArtistAction.GetArtist(artistID)
		)
	}
	
	BackHandler {
		navController.popBackStack()
	}
	
	LazyColumn(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		item {
			SmallTopAppBar(
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = Color.Transparent
				),
				title = {
					Text(
						text = state.artist.name,
						style = MaterialTheme.typography.titleMedium.copy(
							fontWeight = FontWeight.Bold
						)
					)
				},
				navigationIcon = {
					IconButton(
						onClick = {
							navController.popBackStack()
						}
					) {
						Icon(
							imageVector = Icons.Rounded.ArrowBack,
							contentDescription = null
						)
					}
				}
			)
			
			CompositionLocalProvider(
				LocalRippleTheme provides ClearRippleTheme
			) {
				ArtistItem(
					artist = state.artist,
					containerColor = MaterialTheme.colorScheme.surfaceVariant,
					onClick = {},
					modifier = Modifier
						.padding(
							vertical = 8.dp,
							horizontal = 16.dp
						)
				)
			}
		}
		
		items(
			items = state.artist.songs,
			key = { song: Song -> song.hashCode() }
		) { song ->
			SongItem(
				song = song,
				showAlbumImage = false,
				isMusicPlaying = musicomposeState.currentSongPlayed.audioID == song.audioID,
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
