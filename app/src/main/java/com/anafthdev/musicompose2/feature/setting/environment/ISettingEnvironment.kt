package com.anafthdev.musicompose2.feature.setting.environment

import kotlinx.coroutines.CoroutineDispatcher

interface ISettingEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
}