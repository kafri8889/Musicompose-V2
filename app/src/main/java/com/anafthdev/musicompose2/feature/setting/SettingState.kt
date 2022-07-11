package com.anafthdev.musicompose2.feature.setting

import com.anafthdev.musicompose2.data.SkipForwardBackward

data class SettingState(
	val skipForwardBackward: SkipForwardBackward = SkipForwardBackward.FIVE_SECOND
)
