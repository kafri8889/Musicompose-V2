package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MusicomposeTopAppBar(
	modifier: Modifier = Modifier,
	navigationIcon: @Composable () -> Unit,
	actions: @Composable RowScope.() -> Unit,
) {
	
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.height(48.dp)
			.background(MaterialTheme.colorScheme.background)
			.padding(4.dp)
	) {
		navigationIcon()
		
		Spacer(modifier = Modifier.weight(1f))
		
		actions()
	}
	
}
