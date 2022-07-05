package com.anafthdev.musicompose2.feature.playlist_list

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.playlist_list.environment.IPlaylistListEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistListViewModel @Inject constructor(
	playlistListEnvironment: IPlaylistListEnvironment
): StatefulViewModel<PlaylistListState, Unit, PlaylistListAction, IPlaylistListEnvironment>(
	PlaylistListState(),
	playlistListEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getPlaylists().collect { playlists ->
				setState {
					copy(
						playlists = playlists
					)
				}
			}
		}
	}
	
	override fun dispatch(action: PlaylistListAction) {
		when (action) {
			is PlaylistListAction.NewPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.newPlaylist(action.playlist)
				}
			}
		}
	}
	
}