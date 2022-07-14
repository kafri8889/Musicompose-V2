package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.data.ScanOptions
import com.anafthdev.musicompose2.foundation.extension.getLabel
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanOptionsItem(
	checked: Boolean,
	option: ScanOptions,
	modifier: Modifier = Modifier,
	onCheckedChange: (Boolean) -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.clickable { onCheckedChange(!checked) }
			.then(modifier)
	) {
		Text(
			text = option.getLabel(),
			style = MaterialTheme.typography.titleSmall.copy(
				fontFamily = Inter
			),
			modifier = Modifier
				.padding(horizontal = 8.dp)
				.weight(1f)
		)
		
		Checkbox(
			checked = checked,
			onCheckedChange = onCheckedChange
		)
	}
}
