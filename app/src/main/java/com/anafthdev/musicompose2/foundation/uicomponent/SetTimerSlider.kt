package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.foundation.extension.VerticalLines

@Composable
fun SetTimerSlider(
	@FloatRange(from = 0.0, to = 90.0) value: Float,
	onValueChange: (Float) -> Unit,
	modifier: Modifier = Modifier
) {
	
	val tickItems = listOf(
		stringResource(id = R.string.off),
		stringResource(id = R.string.thirty_minute),
		stringResource(id = R.string.sixty_minute),
		stringResource(id = R.string.ninety_minute)
	)
	
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.fillMaxWidth()
	) {
		
		VerticalLines(
			items = tickItems,
			modifier = Modifier
				.zIndex(2f),
			style = MaterialTheme.typography.bodySmall.copy(
				textAlign = TextAlign.Center,
				color = LocalContentColor.current
			)
		)
		
		Slider(
			value = value,
			valueRange = 0f..90f,
			steps = 90,
			onValueChange = { newValue ->
				onValueChange(newValue)
			},
			colors = SliderDefaults.colors(
				activeTickColor = Color.Transparent,
				inactiveTrackColor = MaterialTheme.colorScheme.inverseOnSurface,
				inactiveTickColor = Color.Transparent,
			)
		)
	}
}

