package com.anafthdev.musicompose2.feature.play_queue

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.foundation.theme.circle

@Composable
fun PlayQueueScreen(
	isExpanded: Boolean,
	onBack: () -> Unit
) {
	
	BackHandler(isExpanded) {
		onBack()
	}
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surfaceVariant)
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.fillMaxWidth()
				.height(56.dp)  // Bottom sheet peek height
		) {
			Box(
				modifier = Modifier
					.fillMaxWidth(0.1f)
					.height(4.dp)
					.clip(circle)
					.background(MaterialTheme.colorScheme.primary)
			)
		}
	}
}
