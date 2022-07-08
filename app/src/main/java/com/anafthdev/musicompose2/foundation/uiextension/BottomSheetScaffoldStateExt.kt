package com.anafthdev.musicompose2.foundation.uiextension

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float
	get() {
		val fraction = bottomSheetState.progress.fraction
		val targetValue = bottomSheetState.targetValue
		val currentValue = bottomSheetState.currentValue
		
		return when {
			currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 0f
			currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 1f
			currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> fraction
			else -> 1f - fraction
		}
	}
