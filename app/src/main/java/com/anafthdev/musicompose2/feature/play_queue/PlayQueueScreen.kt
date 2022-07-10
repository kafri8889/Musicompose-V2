package com.anafthdev.musicompose2.feature.play_queue

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isSelected
import com.anafthdev.musicompose2.foundation.theme.circle
import com.anafthdev.musicompose2.foundation.uicomponent.SongQueueItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayQueueScreen(
	isExpanded: Boolean,
	onBack: () -> Unit
) {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
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
		
		LazyColumn {
			items(
				items = musicomposeState.currentSongQueue,
				key = { item: Song -> item.audioID }
			) { song ->
				SongQueueItem(
					song = song,
					selected = song.isSelected(),
					onClick = {
						songController?.play(song)
					},
					modifier = Modifier
						.fillParentMaxWidth()
						.animateItemPlacement(
							animationSpec = tween(400)
						)
				)
			}
		}
	}
}
