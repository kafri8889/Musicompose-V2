package com.anafthdev.musicompose2.feature.musicompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.feature.theme.Musicompose2
import com.anafthdev.musicompose2.feature.theme.black01
import com.anafthdev.musicompose2.feature.theme.black10
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.common.MusicomposeRippleTheme
import com.anafthdev.musicompose2.foundation.common.SongController
import com.anafthdev.musicompose2.foundation.extension.isDark
import com.anafthdev.musicompose2.foundation.extension.isDynamicDark
import com.anafthdev.musicompose2.foundation.uimode.UiModeViewModel
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode
import com.anafthdev.musicompose2.runtime.navigation.MusicomposeNavHost

@Composable
fun Musicompose(
	appDatastore: AppDatastore,
	songController: SongController,
	viewModel: MusicomposeViewModel,
) {
	
	val uiModeViewModel = hiltViewModel<UiModeViewModel>()
	
	val state by viewModel.state.collectAsState()
	val uiModeState by uiModeViewModel.state.collectAsState()
	
	val useDynamicColor by appDatastore.isUseDynamicColor.collectAsState(false)
	val isSystemInDarkTheme = uiModeState.uiMode.isDark() or uiModeState.uiMode.isDynamicDark()
	
	CompositionLocalProvider(
		LocalUiMode provides uiModeState.uiMode,
		LocalRippleTheme provides MusicomposeRippleTheme,
		LocalContentColor provides if (isSystemInDarkTheme) black10 else black01,
		LocalSongController provides songController,
		LocalMusicomposeState provides state
	) {
		Musicompose2(
			darkTheme = isSystemInDarkTheme,
			dynamicColor = useDynamicColor
		) {
			Column {
				MusicomposeNavHost(
					modifier = Modifier
						.fillMaxSize()
				)
			}
		}
	}
	
}
