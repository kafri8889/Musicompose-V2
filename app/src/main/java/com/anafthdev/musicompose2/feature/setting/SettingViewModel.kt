package com.anafthdev.musicompose2.feature.setting

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.setting.environment.ISettingEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
	settingEnvironment: ISettingEnvironment
): StatefulViewModel<SettingState, Unit, SettingAction, ISettingEnvironment>(
	SettingState(),
	settingEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getSkipForwardBackward().collect { skipForwardBackward ->
				setState {
					copy(
						skipForwardBackward = skipForwardBackward
					)
				}
			}
		}
	}
	
	override fun dispatch(action: SettingAction) {
		when (action) {
			is SettingAction.SetSkipForwardBackward -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSkipForwardBackward(action.skipForwardBackward)
				}
			}
		}
	}
}