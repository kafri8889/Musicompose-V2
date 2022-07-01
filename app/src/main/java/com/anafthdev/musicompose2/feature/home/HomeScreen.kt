package com.anafthdev.musicompose2.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState

@Composable
fun HomeScreen() {
	
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<HomeViewModel>()
	
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
	
	}
}
