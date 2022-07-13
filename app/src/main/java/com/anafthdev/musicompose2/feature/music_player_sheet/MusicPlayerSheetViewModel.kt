package com.anafthdev.musicompose2.feature.music_player_sheet

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.music_player_sheet.environment.IMusicPlayerSheetEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerSheetViewModel @Inject constructor(
	musicPlayerSheetEnvironment: IMusicPlayerSheetEnvironment
): StatefulViewModel<MusicPlayerSheetState, Unit, MusicPlayerSheetAction, IMusicPlayerSheetEnvironment>(
	MusicPlayerSheetState(),
	musicPlayerSheetEnvironment
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
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isTimerActive().collect { isActive ->
				setState {
					copy(
						isTimerActive = isActive
					)
				}
			}
		}
	}
	
	override fun dispatch(action: MusicPlayerSheetAction) {
		when (action) {
			is MusicPlayerSheetAction.AddToPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.addToPlaylist(action.song, action.playlist)
				}
			}
			is MusicPlayerSheetAction.SetTimer -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setTimer(action.duration)
				}
			}
		}
	}
	
}