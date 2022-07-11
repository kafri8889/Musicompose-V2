package com.anafthdev.musicompose2.feature.setting

import com.anafthdev.musicompose2.data.SkipForwardBackward

sealed interface SettingAction {
	data class SetSkipForwardBackward(val skipForwardBackward: SkipForwardBackward): SettingAction
}