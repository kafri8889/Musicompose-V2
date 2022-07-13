package com.anafthdev.musicompose2.feature.playlist.playlist_sheet

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.playlist.playlist_sheet.environment.IPlaylistSheetEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistSheetViewModel @Inject constructor(
	playlistSheetEnvironment: IPlaylistSheetEnvironment
): StatefulViewModel<PlaylistSheetState, Unit, PlaylistSheetAction, IPlaylistSheetEnvironment>(
	PlaylistSheetState(),
	playlistSheetEnvironment
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
	
	override fun dispatch(action: PlaylistSheetAction) {
		when (action) {
			is PlaylistSheetAction.ChangePlaylistName -> {
				viewModelScope.launch(environment.dispatcher) {
					setState {
						copy(
							playlistName = action.name
						)
					}
				}
			}
			is PlaylistSheetAction.CreatePlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.createPlaylist(action.name)
				}
			}
			is PlaylistSheetAction.GetPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setPlaylist(action.playlistID)
				}
			}
			is PlaylistSheetAction.UpdatePlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.updatePlaylist(action.playlist)
				}
			}
		}
	}
}