package com.anafthdev.musicompose2.feature.musicompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.datastore.LocalAppDatastore
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.common.MusicomposeRippleTheme
import com.anafthdev.musicompose2.foundation.common.SongController
import com.anafthdev.musicompose2.foundation.extension.isDark
import com.anafthdev.musicompose2.foundation.extension.isDynamicDark
import com.anafthdev.musicompose2.foundation.extension.isDynamicLight
import com.anafthdev.musicompose2.foundation.theme.Musicompose2
import com.anafthdev.musicompose2.foundation.theme.black01
import com.anafthdev.musicompose2.foundation.theme.black10
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayer
import com.anafthdev.musicompose2.foundation.uimode.UiModeViewModel
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode
import com.anafthdev.musicompose2.runtime.navigation.MusicomposeNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Musicompose(
	appDatastore: AppDatastore,
	songController: SongController,
	viewModel: MusicomposeViewModel,
) {
	
	val lifeCycleOwner = LocalLifecycleOwner.current
	
	val uiModeViewModel = hiltViewModel<UiModeViewModel>()
	
	val state by viewModel.state.collectAsState()
	val uiModeState by uiModeViewModel.state.collectAsState()
	
	val useDynamicColor = uiModeState.uiMode.isDynamicDark() or uiModeState.uiMode.isDynamicLight()
	val isSystemInDarkTheme = uiModeState.uiMode.isDark() or uiModeState.uiMode.isDynamicDark()
	
	val scope = rememberCoroutineScope()
	val systemUiController = rememberSystemUiController()
	
	DisposableEffect(lifeCycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			when (event) {
				Lifecycle.Event.ON_CREATE -> {
					viewModel.dispatch(MusicomposeAction.PlayLastSongPlayed)
					scope.launch {
						delay(800)
						songController.showBottomMusicPlayer()
					}
				}
				else -> {}
			}
		}
		
		lifeCycleOwner.lifecycle.addObserver(observer)
		onDispose {
			lifeCycleOwner.lifecycle.removeObserver(observer)
		}
	}
	
	CompositionLocalProvider(
		LocalUiMode provides uiModeState.uiMode,
		LocalRippleTheme provides MusicomposeRippleTheme,
		LocalAppDatastore provides appDatastore,
		LocalContentColor provides if (isSystemInDarkTheme) black10 else black01,
		LocalSongController provides songController,
		LocalMusicomposeState provides state,
		LocalOverscrollConfiguration provides null
	) {
		Musicompose2(
			darkTheme = isSystemInDarkTheme,
			dynamicColor = useDynamicColor
		) {
			val backgroundColor = MaterialTheme.colorScheme.background
			
			SideEffect {
				systemUiController.setSystemBarsColor(
					color = Color.Transparent,
					darkIcons = backgroundColor.luminance() > 0.5f
				)
			}
			
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(MaterialTheme.colorScheme.background)
			) {
				MusicomposeNavHost(
					modifier = Modifier
						.fillMaxSize()
				)
				
				AnimatedVisibility(
					visible = state.isBottomMusicPlayerShowed,
					enter = slideInVertically(
						initialOffsetY = { it }
					),
					exit = slideOutVertically(
						targetOffsetY = { it }
					),
					modifier = Modifier
						.navigationBarsPadding()
						.fillMaxWidth()
						.align(Alignment.BottomCenter)
				) {
					BottomMusicPlayer(
						isPlaying = state.isPlaying,
						currentSong = state.currentSongPlayed,
						onClick = {
						
						},
						onFavoriteClicked = { isFavorite ->
							songController.setFavorite(isFavorite)
						},
						onPlayPauseClicked = { isPlaying ->
							if (isPlaying) songController.resume()
							else songController.pause()
						}
					)
				}
			}
		}
	}
	
}
