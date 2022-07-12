package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isInDarkTheme
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.black01
import com.anafthdev.musicompose2.foundation.theme.black10
import com.anafthdev.musicompose2.foundation.theme.circle
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode

@Composable
fun PlayAll(
	songs: List<Song>,
	modifier: Modifier = Modifier
) {
	
	val uiMode = LocalUiMode.current
	val songController = LocalSongController.current

	Column(
		modifier = Modifier
			.clickable {
				songController?.playAll(songs)
			}
			.then(modifier)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
		) {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.padding(vertical = 8.dp)
					.size(32.dp)
					.clip(circle)
					.background(MaterialTheme.colorScheme.primary)
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_play_filled_rounded),
					contentDescription = null,
					tint = if (uiMode.isInDarkTheme()) black01 else black10,
					modifier = Modifier
						.size(16.dp)
				)
			}
			
			Spacer(modifier = Modifier.width(8.dp))
			
			Text(
				text = stringResource(id = R.string.play_all),
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Bold,
				)
			)
			
			Spacer(modifier = Modifier.width(8.dp))
			
			Text(
				text = stringResource(
					id = R.string.n_song,
					songs.size.toString()
				),
				style = MaterialTheme.typography.bodyMedium.copy(
					fontFamily = Inter,
					color = Color.Gray
				)
			)
		}
		
		Divider(
			color = Color.Gray,
			modifier = Modifier.fillMaxWidth()
		)
	}
}
