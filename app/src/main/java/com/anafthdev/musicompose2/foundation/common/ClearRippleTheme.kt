package com.anafthdev.musicompose2.foundation.common

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ClearRippleTheme = object: RippleTheme {
	@Composable
	override fun defaultColor(): Color = Color.Transparent
	
	@Composable
	override fun rippleAlpha() = RippleAlpha(
		draggedAlpha = 0.0f,
		focusedAlpha = 0.0f,
		hoveredAlpha = 0.0f,
		pressedAlpha = 0.0f,
	)
}
