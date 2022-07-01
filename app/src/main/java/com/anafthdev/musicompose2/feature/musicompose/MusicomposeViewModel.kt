package com.anafthdev.musicompose2.feature.musicompose

import com.anafthdev.musicompose2.feature.musicompose.environment.IMusicomposeEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicomposeViewModel @Inject constructor(
	environment: IMusicomposeEnvironment
): StatefulViewModel<MusicomposeState, Unit, Unit, IMusicomposeEnvironment>(
	MusicomposeState(),
	environment
) {
	
	override fun dispatch(action: Unit) {}
	
}