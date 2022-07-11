package com.anafthdev.musicompose2.feature.setting.environment

import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class SettingEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): ISettingEnvironment {
	
	override fun getSkipForwardBackward(): Flow<SkipForwardBackward> {
		return appDatastore.getSkipForwardBackward
	}
	
	override suspend fun setSkipForwardBackward(skipForwardBackward: SkipForwardBackward) {
		appDatastore.setSkipForwardBackward(skipForwardBackward)
	}
	
}