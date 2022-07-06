package com.anafthdev.musicompose2.feature.artist.artist

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.artist.artist.environment.IArtistEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
	artistEnvironment: IArtistEnvironment
): StatefulViewModel<ArtistState, Unit, ArtistAction, IArtistEnvironment>(
	ArtistState(),
	artistEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getArtist().collect { artist ->
				setState {
					copy(
						artist = artist
					)
				}
			}
		}
	}
	
	override fun dispatch(action: ArtistAction) {
		when (action) {
			is ArtistAction.GetArtist -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setArtist(action.artistID)
				}
			}
		}
	}
	
}