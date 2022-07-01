package com.anafthdev.musicompose2.feature.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.anafthdev.musicompose2.foundation.extension.isInDarkTheme
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode

val MusicomposeRippleTheme: RippleTheme = object : RippleTheme {
	
	@Composable
	override fun defaultColor(): Color {
		val isInDarkTheme = LocalUiMode.current.isInDarkTheme()
		return if (isInDarkTheme) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
	}
	
	@Composable
	override fun rippleAlpha(): RippleAlpha {
		return RippleTheme
			.defaultRippleAlpha(
				contentColor = MaterialTheme.colorScheme.background,
				lightTheme = LocalUiMode.current.isInDarkTheme()
			)
	}
}
