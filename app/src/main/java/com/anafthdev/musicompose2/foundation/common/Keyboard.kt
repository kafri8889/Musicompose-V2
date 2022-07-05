package com.anafthdev.musicompose2.foundation.common

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

enum class Keyboard {
	Show, Hide
}

@Composable
fun keyboardAsState(): State<Keyboard> {
	val keyboardState = remember { mutableStateOf(Keyboard.Hide) }
	val view = LocalView.current
	
	DisposableEffect(view) {
		val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
			val rect = Rect()
			view.getWindowVisibleDisplayFrame(rect)
			val screenHeight = view.rootView.height
			val keypadHeight = screenHeight - rect.bottom
			keyboardState.value = if (keypadHeight > screenHeight * 0.15) Keyboard.Show else Keyboard.Hide
		}
		view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
		
		onDispose {
			view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
		}
	}
	
	return keyboardState
}

fun Keyboard.isShowed(): Boolean = this == Keyboard.Show

fun Keyboard.isHide(): Boolean = this == Keyboard.Hide
