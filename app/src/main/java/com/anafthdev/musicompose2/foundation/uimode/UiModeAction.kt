package com.anafthdev.musicompose2.foundation.uimode

import com.anafthdev.musicompose2.foundation.uimode.data.UiMode

sealed class UiModeAction {
	data class SetUiMode(val uiMode: UiMode): UiModeAction()
}
