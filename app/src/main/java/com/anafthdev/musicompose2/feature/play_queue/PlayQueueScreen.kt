package com.anafthdev.musicompose2.feature.play_queue

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isSelected
import com.anafthdev.musicompose2.foundation.extension.move
import com.anafthdev.musicompose2.foundation.extension.swap
import com.anafthdev.musicompose2.foundation.theme.circle
import com.anafthdev.musicompose2.foundation.uicomponent.SongQueueItem
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun PlayQueueScreen(
	isExpanded: Boolean,
	onBack: () -> Unit
) {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val songQueue = remember { mutableStateListOf<Song>() }

	val reorderableState = rememberReorderableLazyListState(
		onDragEnd = { from, to ->
			songController?.updateQueueSong(
				musicomposeState.currentSongQueue
					.toMutableList()
					.move(from, to)
			)
		},
		onMove = { from, to ->
			songQueue.swap(
				songQueue.move(from.index, to.index)
			)
		}
	)
	
	LaunchedEffect(musicomposeState.currentSongQueue) {
		songQueue.swap(musicomposeState.currentSongQueue)
	}

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

		LazyColumn(
			state = reorderableState.listState,
			modifier = Modifier
				.reorderable(reorderableState)
				.detectReorderAfterLongPress(reorderableState)
		) {
			items(
				items = songQueue,
				key = { item: Song -> item.hashCode() }
			) { song ->
				ReorderableItem(
					reorderableState = reorderableState,
					key = song.hashCode()
				) { isDragging ->
					val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
					
					SongQueueItem(
						song = song,
						elevation = elevation,
						selected = song.isSelected(),
						onClick = {
							songController?.play(song)
						},
						modifier = Modifier
							.fillParentMaxWidth()
							.shadow(elevation)
					)
				}
			}
		}
	}
}
