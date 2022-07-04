package com.anafthdev.musicompose2.utils

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Collator
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.anafthdev.musicompose2.foundation.extension.deviceLocale

object AppUtil {
	
	val collator: Collator = Collator.getInstance(deviceLocale).apply {
		strength = Collator.PRIMARY
	}
	
	fun Any?.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
		Handler(Looper.getMainLooper()).post {
			Toast.makeText(context, this.toString(), length).show()
		}
	}
	
	@SuppressLint("ComposableNaming")
	@Composable
	fun Any?.toast(length: Int = Toast.LENGTH_SHORT) {
		Toast.makeText(LocalContext.current, this.toString(), length).show()
	}
	
}