package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.foundation.extension.isInLightTheme
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.no_shape
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode

@Composable
fun SetSkipForwardBackwardPopup(
	skipForwardBackward: SkipForwardBackward,
	onDismissRequest: () -> Unit,
	onChanged: (SkipForwardBackward) -> Unit
) {
	
	val uiMode = LocalUiMode.current
	
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxSize()
			.background(
				if (uiMode.isInLightTheme()) Color.Black.copy(alpha = 0.24f)
				else Color.White.copy(alpha = 0.24f)
			)
			.clickable(
				enabled = true,
				interactionSource = MutableInteractionSource(),
				indication = null,
				onClick = onDismissRequest
			)
	) {
		Column(
			horizontalAlignment = Alignment.End,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth()
				.clip(MaterialTheme.shapes.large)
				.background(MaterialTheme.colorScheme.background)
		) {
			Spacer(modifier = Modifier.height(8.dp))
			
			SkipForwardBackward.values().forEach {
				SkipForwardBackwardItem(
					selected = skipForwardBackward == it,
					skipForwardBackward = it,
					modifier = Modifier
						.fillMaxWidth()
				) {
					onChanged(it)
				}
			}
			
			Button(
				onClick = onDismissRequest,
				modifier = Modifier
					.padding(8.dp)
			) {
				Text(
					text = stringResource(id = R.string.close),
					style = LocalTextStyle.current.copy(
						fontFamily = Inter
					)
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkipForwardBackwardItem(
	selected: Boolean,
	skipForwardBackward: SkipForwardBackward,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {

	Card(
		colors = CardDefaults.cardColors(
			containerColor = Color.Transparent
		),
		shape = no_shape,
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(8.dp)
		) {
			RadioButton(
				selected = selected,
				onClick = onClick
			)
			
			Text(
				text = stringResource(
					id = R.string.n_second,
					when (skipForwardBackward) {
						SkipForwardBackward.FIVE_SECOND -> "5"
						SkipForwardBackward.TEN_SECOND -> "10"
						SkipForwardBackward.FIFTEEN_SECOND -> "15"
					}
				),
				style = MaterialTheme.typography.titleSmall.copy(
					fontFamily = Inter
				),
				modifier = Modifier
					.padding(horizontal = 8.dp)
			)
		}
	}
}
