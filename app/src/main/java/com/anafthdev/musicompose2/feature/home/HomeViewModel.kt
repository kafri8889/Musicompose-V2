package com.anafthdev.musicompose2.feature.home

import com.anafthdev.musicompose2.feature.home.environment.IHomeEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	environment: IHomeEnvironment
): StatefulViewModel<HomeState, Unit, Unit, IHomeEnvironment>(
	HomeState,
	environment
) {
	
	override fun dispatch(action: Unit) {
	
	}
}