package com.anafthdev.musicompose2.feature.musicompose

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.musicompose.environment.IMusicomposeEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicomposeViewModel @Inject constructor(
	environment: IMusicomposeEnvironment
): StatefulViewModel<MusicomposeState, Unit, MusicomposeAction, IMusicomposeEnvironment>(
	MusicomposeState(),
	environment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getSongs().collect { songs ->
				setState {
					copy(
						songs = songs
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getCurrentPlayedSong().collect { song ->
				setState {
					copy(
						currentSongPlayed = song
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getCurrentPlayedSong().collect { song ->
				setState {
					copy(
						currentSongPlayed = song
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isPlaying().collect { isPlaying ->
				setState {
					copy(
						isPlaying = isPlaying
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isBottomMusicPlayerShowed().collect { isShowed ->
				setState {
					copy(
						isBottomMusicPlayerShowed = isShowed
					)
				}
			}
		}
	}
	
	override fun dispatch(action: MusicomposeAction) {
		when (action) {
			is MusicomposeAction.Play -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.play(action.song)
				}
			}
			is MusicomposeAction.SetPlaying -> {
				viewModelScope.launch(environment.dispatcher) {
					if (action.isPlaying) environment.resume()
					else environment.pause()
				}
			}
			is MusicomposeAction.SetFavorite -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setFavorite(action.isFavorite)
				}
			}
			is MusicomposeAction.SetShowBottomMusicPlayer -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setShowBottomMusicPlayer(action.isShowed)
				}
			}
			is MusicomposeAction.PlayLastSongPlayed -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.playLastSongPlayed()
				}
			}
		}
	}
	
}