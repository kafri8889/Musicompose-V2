package com.anafthdev.musicompose2.foundation.extension

import android.graphics.Paint
import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VerticalLines(
	items: List<String>,
	modifier: Modifier = Modifier,
	tickColor: Color = MaterialTheme.colorScheme.secondary,
	style: TextStyle = LocalTextStyle.current
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.fillMaxWidth()
			.height(12.dp)
	) {
		val drawPadding = with(LocalDensity.current) { 8.dp.toPx() }
		
		Canvas(
			modifier = Modifier
				.fillMaxSize()
		) {
			val yStart = 0f
			val yEnd = size.height
			val distance = (size.width.minus(2 * drawPadding)).div(items.size.minus(1))
			items.forEachIndexed { i, s ->
				drawLine(
					color = tickColor,
					start = Offset(
						x = drawPadding + i.times(distance),
						y = yStart
					),
					end = Offset(
						x = drawPadding + i.times(distance),
						y = yEnd
					)
				)
				
				drawContext.canvas.nativeCanvas.drawText(
					s,
					drawPadding + i.times(distance),
					size.height + drawPadding + 12.dp.toPx(),
					TextPaint().apply {
						color = style.color.toArgb()
						textSize = style.fontSize.toPx()
						textAlign = when (i) {
							0 -> Paint.Align.LEFT
							(items.size - 1) -> Paint.Align.RIGHT
							else -> {
								when (style.textAlign) {
									TextAlign.Start -> Paint.Align.LEFT
									TextAlign.Center -> Paint.Align.CENTER
									TextAlign.End -> Paint.Align.RIGHT
									else -> Paint.Align.LEFT
								}
							}
						}
					}
				)
			}
		}
	}
}