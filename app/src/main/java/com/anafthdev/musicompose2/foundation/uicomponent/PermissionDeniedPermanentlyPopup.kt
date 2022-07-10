package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.foundation.theme.Inter

@Composable
fun PermissionDeniedPermanentlyPopup(
	modifier: Modifier = Modifier,
	onClose: () -> Unit,
	onOpen: () -> Unit
) {
	
	AlertDialog(
		modifier = modifier,
		onDismissRequest = {},
		title = {},
		text = {
			Text(stringResource(id = R.string.permission_denied_permanently_message))
		},
		confirmButton = {
			Button(
				onClick = onOpen
			) {
				Text(
					text = stringResource(id = R.string.open),
					style = LocalTextStyle.current.copy(
						fontFamily = Inter
					)
				)
			}
		},
		dismissButton = {
			TextButton(
				onClick = onClose
			) {
				Text(
					text = stringResource(id = R.string.close),
					style = LocalTextStyle.current.copy(
						fontFamily = Inter
					)
				)
			}
		}
	)
}
