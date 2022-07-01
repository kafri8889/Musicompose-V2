package com.anafthdev.musicompose2.feature.musicompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.feature.theme.Musicompose2
import com.anafthdev.musicompose2.foundation.extension.isDark
import com.anafthdev.musicompose2.foundation.extension.isDynamicDark
import com.anafthdev.musicompose2.foundation.uimode.UiModeViewModel
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode

@Composable
fun Musicompose(appDatastore: AppDatastore) {
	
	val viewModel = hiltViewModel<MusicomposeViewModel>()
	val uiModeViewModel = hiltViewModel<UiModeViewModel>()
	
	val state by viewModel.state.collectAsState()
	val uiModeState by uiModeViewModel.state.collectAsState()
	
	val useDynamicColor by appDatastore.isUseDynamicColor.collectAsState(false)
	val isSystemInDarkTheme = uiModeState.uiMode.isDark() or uiModeState.uiMode.isDynamicDark()
	
	CompositionLocalProvider(
		LocalUiMode provides uiModeState.uiMode,
		LocalMusicomposeState provides state
	) {
		Musicompose2(
			darkTheme = isSystemInDarkTheme,
			dynamicColor = useDynamicColor
		) {
		
		}
	}
	
}
