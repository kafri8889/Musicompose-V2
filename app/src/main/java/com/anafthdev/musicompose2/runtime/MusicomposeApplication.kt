package com.anafthdev.musicompose2.runtime

import android.app.Application
import android.os.Build
import com.anafthdev.musicompose2.utils.NotificationUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MusicomposeApplication: Application() {
	
	override fun onCreate() {
		super.onCreate()
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationUtil.createChannel(this)
		}
	}
}