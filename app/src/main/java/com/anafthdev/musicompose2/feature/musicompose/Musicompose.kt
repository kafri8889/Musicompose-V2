package com.anafthdev.musicompose2.feature.musicompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.anafthdev.musicompose2.foundation.uimode.UiModeViewModel
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode
import com.anafthdev.musicompose2.runtime.navigation.MusicomposeNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Musicompose(
	appDatastore: AppDatastore,
	songController: SongController,
	viewModel: MusicomposeViewModel,
) {
	
	val uiModeViewModel = hiltViewModel<UiModeViewModel>()
	
	val state by viewModel.state.collectAsState()
	val uiModeState by uiModeViewModel.state.collectAsState()
	
	val useDynamicColor = uiModeState.uiMode.isDynamicDark() or uiModeState.uiMode.isDynamicLight()
	val isSystemInDarkTheme = uiModeState.uiMode.isDark() or uiModeState.uiMode.isDynamicDark()
	
	val systemUiController = rememberSystemUiController()
	
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
			
			Column {
				MusicomposeNavHost(
					modifier = Modifier
						.fillMaxSize()
						.background(MaterialTheme.colorScheme.background)
				)
			}
		}
	}
	
}
