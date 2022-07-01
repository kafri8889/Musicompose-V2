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
): StatefulViewModel<MusicomposeState, Unit, Unit, IMusicomposeEnvironment>(
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
	}
	
	override fun dispatch(action: Unit) {}
	
}