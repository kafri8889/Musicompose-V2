package com.anafthdev.musicompose2.feature.album_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.foundation.uicomponent.AlbumItem
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault

@Composable
fun AlbumScreen() {
	
	val viewModel = hiltViewModel<AlbumListViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		
		items(
			items = state.albums,
			key = { album: Album -> album.id }
		) { album ->
			AlbumItem(
				album = album,
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
