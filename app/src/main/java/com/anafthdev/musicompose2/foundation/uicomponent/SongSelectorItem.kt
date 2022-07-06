package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SongSelectorItem(
	song: Song,
	contain: Boolean,
	selected: Boolean,
	type: SongSelectorType,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Card(
		onClick = onClick,
		colors = CardDefaults.cardColors(
			containerColor = Color.Transparent
		),
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
				.height(72.dp)
		) {
			
			Image(
				painter = rememberAsyncImagePainter(
					ImageRequest.Builder(LocalContext.current)
						.data(song.albumPath.toUri())
						.error(R.drawable.ic_music_unknown)
						.placeholder(R.drawable.ic_music_unknown)
						.build()
				),
				contentDescription = null,
				modifier = Modifier
					.padding(8.dp)
					.fillMaxHeight()
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.medium)
			)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.weight(1f)
			) {
				Text(
					text = song.title,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium.copy(
						color = if (selected) MaterialTheme.colorScheme.primary
						else LocalContentColor.current,
						fontWeight = FontWeight.Bold
					)
				)
				
				Text(
					text = "${song.artist} • ${song.album}",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodySmall.copy(
						color = if (selected) MaterialTheme.colorScheme.primary
						else LocalContentColor.current.copy(alpha = 0.7f),
						fontFamily = Inter
					)
				)
			}
			
			AnimatedContent(
				targetState = contain,
				transitionSpec = {
					scaleIn(
						animationSpec = tween(400)
					) with scaleOut(animationSpec = tween(400))
				},
				modifier = Modifier
					.padding(8.dp)
			) {
				if (type == SongSelectorType.ADD_SONG) {
					Icon(
						imageVector = if (it) Icons.Rounded.Check else Icons.Rounded.Add,
						contentDescription = null
					)
				} else {
					Image(
						painter = painterResource(
							id = if (it) R.drawable.ic_favorite_selected else R.drawable.ic_favorite_unselected
						),
						contentDescription = null
					)
				}
			}
		}
	}
}
