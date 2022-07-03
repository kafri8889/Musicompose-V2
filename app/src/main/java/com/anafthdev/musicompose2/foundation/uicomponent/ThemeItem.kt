package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.foundation.extension.uiModeToString
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.black01
import com.anafthdev.musicompose2.foundation.theme.black10
import com.anafthdev.musicompose2.foundation.uimode.data.UiMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeItem(
	selected: Boolean,
	uiMode: UiMode,
	colorScheme: ColorScheme,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Card(
		colors = CardDefaults.cardColors(
			containerColor = colorScheme.primary
		),
		shape = MaterialTheme.shapes.large,
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(
					vertical = 8.dp,
					horizontal = 16.dp
				)
				.fillMaxWidth()
		) {
			Text(
				text = uiMode.uiModeToString(),
				style = MaterialTheme.typography.bodyMedium.copy(
					color = if (colorScheme.primary.luminance() > 0.5f) black01 else black10,
					fontFamily = Inter
				)
			)
			
			Spacer(modifier = Modifier.weight(1f))
			
			Spacer(modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize))
			
			if (selected) {
				RadioButton(
					selected = true,
					onClick = onClick,
					colors = RadioButtonDefaults.colors(
						selectedColor = colorScheme.onPrimary
					)
				)
			}
		}
	}
}
