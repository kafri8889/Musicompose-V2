package com.anafthdev.musicompose2.foundation.uimode.environment

import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.foundation.uimode.data.UiMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class UiModeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): IUiModeEnvironment {
	
	override suspend fun getUiMode(): Flow<UiMode> {
		return appDatastore.getUiMode
	}
	
	override suspend fun setUiMode(uiMode: UiMode) {
		appDatastore.setUiMode(uiMode)
	}
	
}