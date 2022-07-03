package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.data.preference.Language
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageItem(
	selected: Boolean,
	language: Language,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Card(
		colors = CardDefaults.cardColors(
			containerColor = if (selected) MaterialTheme.colorScheme.primary
			else CardDefaults.cardColors().containerColor(enabled = true).value
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
				text = language.name,
				style = MaterialTheme.typography.bodyMedium.copy(
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
						selectedColor = MaterialTheme.colorScheme.onPrimary
					)
				)
			}
		}
	}
}
