package com.anafthdev.musicompose2.feature.scan_options

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.scan_options.environment.IScanOptionsEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanOptionsViewModel @Inject constructor(
	scanOptionsEnvironment: IScanOptionsEnvironment
): StatefulViewModel<ScanOptionsState, Unit, ScanOptionsAction, IScanOptionsEnvironment>(
	ScanOptionsState(),
	scanOptionsEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.isTracksSmallerThan100KBSkipped().collect { skip ->
				setState {
					copy(
						isTracksSmallerThan100KBSkipped = skip
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isTracksShorterThan60SecondsSkipped().collect { skip ->
				setState {
					copy(
						isTracksShorterThan60SecondsSkipped = skip
					)
				}
			}
		}
	}
	
	override fun dispatch(action: ScanOptionsAction) {
		when (action) {
			is ScanOptionsAction.SetSkipTracksSmallerThan100KB -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSkipTracksSmallerThan100KB(action.skip)
				}
			}
			is ScanOptionsAction.SetSkipTracksShorterThan60Seconds -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSkipTracksShorterThan60Seconds(action.skip)
				}
			}
		}
	}
	
}