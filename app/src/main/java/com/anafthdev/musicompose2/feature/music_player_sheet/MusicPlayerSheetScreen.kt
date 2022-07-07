package com.anafthdev.musicompose2.feature.music_player_sheet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MusicPlayerSheetScreen(
	navController: NavController
) {
	
	val viewModel = hiltViewModel<MusicPlayerSheetViewModel>()
	
	val state by viewModel.state.collectAsState()

	BackHandler {
		navController.popBackStack()
	}
	
	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
	
	}
	
}
