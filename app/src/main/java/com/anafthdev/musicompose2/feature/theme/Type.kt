package com.anafthdev.musicompose2.feature.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.anafthdev.musicompose2.R

val default = FontFamily.Default

val Inter = FontFamily(
	Font(R.font.inter_thin, FontWeight.Thin),
	Font(R.font.inter_extra_light, FontWeight.ExtraLight),
	Font(R.font.inter_light, FontWeight.Light),
	Font(R.font.inter_regular, FontWeight.Normal),
	Font(R.font.inter_medium, FontWeight.Medium),
	Font(R.font.inter_semi_bold, FontWeight.SemiBold),
	Font(R.font.inter_bold, FontWeight.Bold),
	Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
)

val DMSans = FontFamily(
	Font(R.font.dm_sans_regular, FontWeight.Normal),
	Font(R.font.dm_sans_medium, FontWeight.Medium),
	Font(R.font.dm_sans_bold, FontWeight.Bold),
)

val Typography = Typography(
	displayLarge = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 57.sp,
		lineHeight = 64.sp,
		letterSpacing = (-0.25).sp,
	),
	displayMedium = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 45.sp,
		lineHeight = 52.sp,
		letterSpacing = 0.sp,
	),
	displaySmall = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 36.sp,
		lineHeight = 44.sp,
		letterSpacing = 0.sp,
	),
	headlineLarge = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 32.sp,
		lineHeight = 40.sp,
		letterSpacing = 0.sp,
	),
	headlineMedium = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 28.sp,
		lineHeight = 36.sp,
		letterSpacing = 0.sp,
	),
	headlineSmall = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 24.sp,
		lineHeight = 32.sp,
		letterSpacing = 0.sp,
	),
	titleLarge = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 22.sp,
		lineHeight = 28.sp,
		letterSpacing = 0.sp,
	),
	titleMedium = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.1.sp,
	),
	titleSmall = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	labelLarge = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	bodyLarge = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp,
	),
	bodyMedium = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.25.sp,
	),
	bodySmall = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Normal,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.4.sp,
	),
	labelMedium = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	),
	labelSmall = TextStyle(
		fontFamily = DMSans,
		fontWeight = FontWeight.Medium,
		fontSize = 11.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	)
)