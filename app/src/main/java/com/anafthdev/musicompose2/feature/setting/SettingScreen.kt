package com.anafthdev.musicompose2.feature.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.foundation.extension.isInDarkTheme
import com.anafthdev.musicompose2.foundation.extension.isInLightTheme
import com.anafthdev.musicompose2.foundation.uicomponent.BasicPreference
import com.anafthdev.musicompose2.foundation.uicomponent.GroupPreference
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode

@Composable
fun SettingScreen(
	navController: NavController
) {
	
	val uiMode = LocalUiMode.current
	
	val settingString = stringResource(id = R.string.setting)
	
	val preferences = listOf(
		BasicPreference(
			key = "lang",
			title = stringResource(id = R.string.language),
			summary = stringResource(id = R.string.language_summary),
			iconResId = R.drawable.ic_language,
		),
		BasicPreference(
			key = "ui_mode",
			title = stringResource(id = R.string.theme),
			value = stringResource(
				id = if (uiMode.isInDarkTheme()) R.string.dark else R.string.light
			),
			iconResId = if (uiMode.isInLightTheme()) R.drawable.ic_sun else R.drawable.ic_moon,
			showValue = true
		)
	)

	BackHandler {
		navController.popBackStack()
	}
	
	Column(
		modifier = Modifier
			.statusBarsPadding()
			.background(MaterialTheme.colorScheme.background)
	) {
		SmallTopAppBar(
			colors = TopAppBarDefaults.smallTopAppBarColors(
				containerColor = Color.Transparent
			),
			title = {
				Text(
					text = settingString,
					style = MaterialTheme.typography.titleLarge,
				)
			},
			navigationIcon = {
				IconButton(
					onClick = {
						navController.popBackStack()
					}
				) {
					Icon(
						imageVector = Icons.Rounded.ArrowBack,
						contentDescription = null
					)
				}
			}
		)
		
		GroupPreference(
			preferences = preferences,
			onClick = { preference ->
				when (preference.key) {
					preferences[0].key -> {
						navController.navigate(MusicomposeDestination.Language.route)
					}
					preferences[1].key -> {
						navController.navigate(MusicomposeDestination.Theme.route)
					}
				}
			}
		)
	}
	
}
