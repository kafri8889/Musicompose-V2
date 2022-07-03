package com.anafthdev.musicompose2.feature.setting.environment

import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class SettingEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher
): ISettingEnvironment {

}