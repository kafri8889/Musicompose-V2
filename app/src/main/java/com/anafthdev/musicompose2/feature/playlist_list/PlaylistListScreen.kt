package com.anafthdev.musicompose2.feature.playlist_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.PlaylistItem

@Composable
fun PlaylistListScreen(
	onNewPlaylist: () -> Unit
) {
	
	val viewModel = hiltViewModel<PlaylistListViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	Box(
		modifier = Modifier
			.fillMaxSize()
	) {
		FloatingActionButton(
			onClick = onNewPlaylist,
			modifier = Modifier
				.padding(24.dp)
				.padding(bottom = BottomMusicPlayerDefault.Height)
				.zIndex(2f)
				.align(Alignment.BottomEnd)
		) {
			Icon(
				imageVector = Icons.Rounded.Add,
				contentDescription = null
			)
		}
		
		LazyColumn {
			
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
			
			// BottomMusicPlayer padding
			item {
				Spacer(modifier = Modifier.height(BottomMusicPlayerDefault.Height))
			}
		}
	}

}
