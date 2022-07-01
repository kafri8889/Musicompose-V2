package com.anafthdev.musicompose2.foundation.extension

import com.anafthdev.musicompose2.foundation.uimode.data.UiMode

fun UiMode.isDark(): Boolean = this == UiMode.DARK
fun UiMode.isLight(): Boolean = this == UiMode.LIGHT
fun UiMode.isDynamicDark(): Boolean = this == UiMode.DYNAMIC_DARK
fun UiMode.isDynamicLight(): Boolean = this == UiMode.DYNAMIC_LIGHT
fun UiMode.isInDarkTheme(): Boolean = isDark() or isDynamicDark()
fun UiMode.isInLightTheme(): Boolean = isLight() or isDynamicLight()
