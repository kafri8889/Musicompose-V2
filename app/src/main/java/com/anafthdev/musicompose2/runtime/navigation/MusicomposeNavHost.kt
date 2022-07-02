package com.anafthdev.musicompose2.runtime.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.feature.home.HomeScreen
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.feature.musicompose.data.LocalSongController
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayer
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MusicomposeNavHost(
	modifier: Modifier = Modifier
) {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val bottomSheetNavigator = rememberBottomSheetNavigator()
	val navController = rememberNavController(bottomSheetNavigator)
	
	ModalBottomSheetLayout(
		bottomSheetNavigator = bottomSheetNavigator,
		modifier = modifier
	) {
		Box {
			NavHost(
				navController = navController,
				startDestination = MusicomposeDestination.Home.route,
				modifier = Modifier
					.fillMaxSize()
			) {
				composable(MusicomposeDestination.Home.route) {
					HomeScreen()
				}
			}
			
			BottomMusicPlayer(
				isPlaying = musicomposeState.isPlaying,
				currentSong = musicomposeState.currentSongPlayed,
				onClick = {
				
				},
				onFavoriteClicked = { isFavorite ->
				
				},
				onPlayPauseClicked = { isPlaying ->
					if (isPlaying) songController?.resume()
					else songController?.pause()
				},
				modifier = Modifier
					.navigationBarsPadding()
					.fillMaxWidth()
					.align(Alignment.BottomCenter)
			)
		}
	}
}
