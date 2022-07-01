package com.anafthdev.musicompose2.foundation.uimode

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.foundation.uimode.environment.IUiModeEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UiModeViewModel @Inject constructor(
	uiModeEnvironment: IUiModeEnvironment
): StatefulViewModel<UiModeState, Unit, UiModeAction, IUiModeEnvironment>(UiModeState(), uiModeEnvironment) {
	
	init {
		viewModelScope.launch {
			environment.getUiMode()
				.flowOn(environment.dispatcher)
				.collect { mUiMode ->
					setState {
						copy(
							uiMode = mUiMode
						)
					}
				}
		}
	}
	
	override fun dispatch(action: UiModeAction) {
		when (action) {
			is UiModeAction.SetUiMode -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setUiMode(action.uiMode)
				}
			}
		}
	}
	
}