package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun Preference(
	preference: Preference,
	onClick: (Any) -> Unit
) {
	when (preference) {
		is BasicPreference -> {
			BasicPreference(
				preference = preference,
				onClick = onClick
			)
		}
		
		is SwitchPreference -> {
			SwitchPreference(
				preference = preference,
				onClick = onClick
			)
		}
	}
}

@Composable
fun GroupPreference(
	preferences: List<Preference>,
	modifier: Modifier = Modifier,
	onClick: (Preference) -> Unit
) {
	val groupedPreference = preferences.groupBy { it.category }
	
	groupedPreference.forEach {
		
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.then(modifier)
		) {
			if (it.key.isNotBlank()) {
				Text(
					text = it.key,
					style = MaterialTheme.typography.titleMedium.copy(
						color = MaterialTheme.colorScheme.primary,
						fontWeight = FontWeight.Bold,
					),
					modifier = Modifier
						.padding(
							top = 16.dp,
							bottom = 8.dp,
							start = 12.dp,
							end = 12.dp
						)
				)
			}
			
			it.value.forEach { preference ->
				Preference(
					preference = preference,
					onClick = {
						onClick(
							when (preference) {
								is BasicPreference -> preference.copy(value = it)
								is SwitchPreference -> preference.copy(isChecked = it as Boolean)
							}
						)
					}
				)
			}
		}
	}
}

@Composable
private fun BasicPreference(
	preference: BasicPreference,
	onClick: (Any) -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.height(64.dp)
			.clickable { onClick("") }
	) {
		if (preference.iconResId != null) {
			Icon(
				painter = painterResource(id = preference.iconResId),
				contentDescription = null,
				modifier = Modifier
					.size(24.dp)
					.weight(
						weight = 0.12f
					)
			)
		} else {
			Box(
				modifier = Modifier
					.weight(weight = 0.12f)
			)
		}
		
		Column(
			verticalArrangement = Arrangement.Center,
			modifier = Modifier
				.weight(
					weight = 0.68f
				)
		) {
			Text(
				text = preference.title,
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Medium,
				)
			)
			
			if (preference.summary.isNotBlank()) {
				Text(
					text = preference.summary,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.titleSmall.copy(
						color = Color.Gray,
						fontWeight = FontWeight.Normal
					)
				)
			}
		}
		
		Box(
			contentAlignment = Alignment.CenterEnd,
			modifier = Modifier
				.padding(end = 12.dp)
				.weight(0.2f)
		) {
			if (preference.showValue) {
				Text(
					text = preference.value.toString(),
					textAlign = TextAlign.End,
					style = MaterialTheme.typography.titleMedium.copy(
						fontWeight = FontWeight.Normal,
						color = MaterialTheme.colorScheme.primary
					)
				)
			}
		}
	}
}

@Composable
private fun SwitchPreference(
	preference: SwitchPreference,
	onClick: (Any) -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.height(64.dp)
			.clickable {
				onClick(!preference.isChecked)
			}
	) {
		if (preference.iconResId != null) {
			Icon(
				painter = painterResource(id = preference.iconResId),
				contentDescription = null,
				modifier = Modifier
					.size(24.dp)
					.weight(0.12f)
			)
		} else {
			Box(
				modifier = Modifier
					.weight(weight = 0.12f)
			)
		}
		
		Column(
			verticalArrangement = Arrangement.Center,
			modifier = Modifier
				.weight(0.68f)
		) {
			Text(
				text = preference.title,
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Medium,
				)
			)
			
			if (preference.summary.isNotBlank()) {
				Text(
					text = preference.summary,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.titleSmall.copy(
						color = Color.Gray,
						fontWeight = FontWeight.Normal
					)
				)
			}
		}
		
		Switch(
			checked = preference.isChecked,
			onCheckedChange = {
				onClick(!preference.isChecked)
			},
			modifier = Modifier
				.weight(0.2f)
		)
	}
}

sealed interface Preference {
	val key: String
	val title: String
	val summary: String
	val category: String
	val iconResId: Int?
}

data class BasicPreference(
	override val key: String,
	override val title: String,
	override val summary: String = "",
	override val category: String = "",
	override val iconResId: Int? = null,
	var value: Any = "",
	var showValue: Boolean = false
): Preference

data class SwitchPreference(
	override val key: String,
	override val title: String,
	override val summary: String = "",
	override val category: String = "",
	override val iconResId: Int? = null,
	var isChecked: Boolean,
): Preference
