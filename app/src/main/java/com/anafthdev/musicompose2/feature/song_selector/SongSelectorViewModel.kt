package com.anafthdev.musicompose2.feature.song_selector

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.song_selector.environment.ISongSelectorEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongSelectorViewModel @Inject constructor(
	songSelectorEnvironment: ISongSelectorEnvironment
): StatefulViewModel<SongSelectorState, Unit, SongSelectorAction, ISongSelectorEnvironment>(
	SongSelectorState(),
	songSelectorEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getSearchQuery().collect { query ->
				setState {
					copy(
						query = query
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSelectedSong().collect { songs ->
				setState {
					copy(
						selectedSong = songs
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSongs().collect { songs ->
				setState {
					copy(
						songs = songs
					)
				}
			}
		}
	}
	
	override fun dispatch(action: SongSelectorAction) {
		when (action) {
			is SongSelectorAction.Search -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.search(action.query)
				}
			}
			is SongSelectorAction.AddSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.addSong(action.song)
				}
			}
			is SongSelectorAction.RemoveSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.removeSong(action.song)
				}
			}
			is SongSelectorAction.GetPlaylist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.getPlaylist(action.playlistID)
				}
			}
			is SongSelectorAction.SetSongSelectorType -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSongSelectorType(action.type)
				}
			}
		}
	}
}