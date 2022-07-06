package com.anafthdev.musicompose2.feature.delete_playlist

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.delete_playlist.environment.IDeletePlaylistEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletePlaylistViewModel @Inject constructor(
	deletePlaylistEnvironment: IDeletePlaylistEnvironment
): StatefulViewModel<DeletePlaylistState, Unit, DeletePlaylistAction, IDeletePlaylistEnvironment>(
	DeletePlaylistState(),
	deletePlaylistEnvironment
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
	
	override fun dispatch(action: DeletePlaylistAction) {
		when (action) {
			is DeletePlaylistAction.GetPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setPlaylist(action.playlistID)
				}
			}
			is DeletePlaylistAction.DeletePlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.deletePlaylist(action.playlist)
				}
			}
		}
	}
}