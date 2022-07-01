package com.anafthdev.musicompose2.foundation.extension

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

/**
 * convert ARGB color int to [Color]
 * @author kafri8889
 */
fun Int.toColor(): Color = Color(this)

/**
 * darken the color
 * @param factor shade factor (from 0 to 1)
 * @author kafri8889
 */
fun Color.darkenColor(
	@FloatRange(from = 0.0, to = 1.0) factor: Float,
	apply: Boolean = true
): Color {
	return if (apply) Color(
		red = this.red * (1 - factor),
		green = this.green * (1 - factor),
		blue = this.blue * (1 - factor)
	) else this
}

/**
 * lighten the color
 * @param factor tint factor (from 0 to 1)
 * @author kafri8889
 */
fun Color.lightenColor(
	@FloatRange(from = 0.0, to = 1.0) factor: Float,
	apply: Boolean = true
): Color {
	return if (apply) Color(
		red = this.red + (1 - this.red) * factor,
		green = this.green + (1 - this.green) * factor,
		blue = this.blue + (1 - this.blue) * factor
	) else this
}

/**
 * darken color if luminance greater than `moreThanLuminance`
 * @param moreThanLuminance luminance in percent
 * @param factor darken factor
 *
 * @author kafri8889
 */
fun Color.darkenColor(
	@FloatRange(from = 0.0, to = 100.0) moreThanLuminance: Float,
	factor: Float
): Color {
	val luminancePercent = luminance() * 100
	return if (luminancePercent > moreThanLuminance) darkenColor(factor) else this
}
