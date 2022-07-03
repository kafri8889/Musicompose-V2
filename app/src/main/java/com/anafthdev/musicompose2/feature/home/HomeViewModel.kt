package com.anafthdev.musicompose2.feature.home

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.home.environment.IHomeEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	environment: IHomeEnvironment
): StatefulViewModel<HomeState, Unit, HomeAction, IHomeEnvironment>(
	HomeState,
	environment
) {
	
	override fun dispatch(action: HomeAction) {
		when (action) {
			is HomeAction.UpdateSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.updateSong(action.song)
				}
			}
		}
	}
	
}