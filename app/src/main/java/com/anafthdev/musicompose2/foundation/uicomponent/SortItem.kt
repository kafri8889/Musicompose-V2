package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortItem(
	text: String,
	selected: Boolean,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.clickable(onClick = onClick)
	) {
		Text(
			text = text,
			style = MaterialTheme.typography.titleSmall.copy(
				fontFamily = Inter
			),
			modifier = Modifier
				.wrapContentWidth(Alignment.Start)
				.padding(top = 14.dp, bottom = 14.dp, start = 16.dp)
		)
		
		Spacer(modifier = Modifier.weight(1f))
		
		RadioButton(
			selected = selected,
			onClick = onClick,
			modifier = Modifier
				.wrapContentSize(Alignment.CenterEnd)
				.padding(end = 16.dp)
		)
	}
}
