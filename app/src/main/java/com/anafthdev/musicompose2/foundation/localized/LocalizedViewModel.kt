package com.anafthdev.musicompose2.foundation.localized

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.foundation.localized.environment.ILocalizedEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalizedViewModel @Inject constructor(
	localizedEnvironment: ILocalizedEnvironment
): StatefulViewModel<LocalizedState, LocalizedEffect, LocalizedAction, ILocalizedEnvironment>(
	LocalizedState(), localizedEnvironment) {
	
	init {
		viewModelScope.launch {
			environment.getLanguage()
				.flowOn(environment.dispatcher)
				.collect {
					setEffect(LocalizedEffect.ApplyLanguage(it))
					setState { copy(language = it) }
				}
		}
	}
	
	override fun dispatch(action: LocalizedAction) {
		when (action) {
			is LocalizedAction.SetLanguage -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setLanguage(action.lang)
				}
			}
		}
	}
	
}