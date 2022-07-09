package com.anafthdev.musicompose2.foundation.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Toast this object
 *
 * @author kafri8889
 */
fun Any.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
	Handler(Looper.getMainLooper()).post {
		Toast.makeText(context, this.toString(), length).show()
	}
}

/**
 * Toast this object
 *
 * @author kafri8889
 */
@SuppressLint("ComposableNaming")
@Composable
fun Any.toast(length: Int = Toast.LENGTH_SHORT) {
	Toast.makeText(LocalContext.current, this.toString(), length).show()
}
