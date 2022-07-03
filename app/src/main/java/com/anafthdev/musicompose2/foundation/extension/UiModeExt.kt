package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.foundation.uimode.data.UiMode

fun UiMode.isDark(): Boolean = this == UiMode.DARK
fun UiMode.isLight(): Boolean = this == UiMode.LIGHT
fun UiMode.isDynamicDark(): Boolean = this == UiMode.DYNAMIC_DARK
fun UiMode.isDynamicLight(): Boolean = this == UiMode.DYNAMIC_LIGHT
fun UiMode.isInDarkTheme(): Boolean = isDark() or isDynamicDark()
fun UiMode.isInLightTheme(): Boolean = isLight() or isDynamicLight()

@Composable
fun UiMode.uiModeToString(): String {
	return stringResource(
		id = when (this) {
			UiMode.DARK -> R.string.dark
			UiMode.LIGHT -> R.string.light
			UiMode.DYNAMIC_DARK -> R.string.dynamic_dark
			UiMode.DYNAMIC_LIGHT -> R.string.dynamic_light
		}
	)
}
