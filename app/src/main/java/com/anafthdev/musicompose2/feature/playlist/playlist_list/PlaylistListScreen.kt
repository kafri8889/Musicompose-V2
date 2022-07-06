package com.anafthdev.musicompose2.feature.playlist.playlist_list

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.PlaylistItem

@Composable
fun PlaylistListScreen(
	navController: NavController,
	onNewPlaylist: () -> Unit
) {
	
	val musicomposeState = LocalMusicomposeState.current
	
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
				
				val songs = remember(playlist, musicomposeState.songs) {
					playlist.songs.map { songID ->
						musicomposeState.songs.find { it.audioID == songID } ?: Song.default
					}.filterNot { it.audioID == Song.default.audioID }
				}
				
				PlaylistItem(
					playlist = playlist,
					songs = songs,
					onClick = {
						navController.navigate(
							MusicomposeDestination.Playlist.createRoute(
								playlistID = playlist.id
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

}
