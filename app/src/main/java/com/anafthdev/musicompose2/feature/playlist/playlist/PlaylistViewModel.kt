package com.anafthdev.musicompose2.feature.playlist.playlist

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.playlist.playlist.environment.IPlaylistEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
	playlistEnvironment: IPlaylistEnvironment
): StatefulViewModel<PlaylistState, Unit, PlaylistAction, IPlaylistEnvironment>(
	PlaylistState(),
	playlistEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getPlaylist().collect { playlist ->
				setState {
					copy(
						playlist = playlist
					)
				}
			}
		}
	}
	
	override fun dispatch(action: PlaylistAction) {
		when (action) {
			is PlaylistAction.GetPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setPlaylist(action.playlistID)
				}
			}
		}
	}
	
}