package com.anafthdev.musicompose2.feature.playlist_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.foundation.uicomponent.PlaylistItem

@Composable
fun PlaylistListScreen() {
	
	val viewModel = hiltViewModel<PlaylistListViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
	) {
		
		items(
			items = state.playlists,
			key = { playlist: Playlist -> playlist.hashCode() }
		) { playlist ->
			PlaylistItem(
				playlist = playlist,
				onClick = {
				
				}
			)
		}
		
	}

}
