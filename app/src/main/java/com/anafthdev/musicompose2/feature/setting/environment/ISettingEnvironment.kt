package com.anafthdev.musicompose2.feature.setting.environment

import com.anafthdev.musicompose2.data.SkipForwardBackward
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ISettingEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSkipForwardBackward(): Flow<SkipForwardBackward>
	
	suspend fun setSkipForwardBackward(skipForwardBackward: SkipForwardBackward)
	
}