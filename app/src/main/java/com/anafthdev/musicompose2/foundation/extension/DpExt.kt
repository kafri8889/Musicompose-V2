package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

@Composable
fun Int.toDp(): Dp {
	return with(LocalDensity.current) { toDp() }
}

fun Int.toDp(density: Density): Dp {
	return with(density) { toDp() }
}
