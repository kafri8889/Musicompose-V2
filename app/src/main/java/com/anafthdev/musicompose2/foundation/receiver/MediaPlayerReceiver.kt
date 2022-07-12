package com.anafthdev.musicompose2.foundation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.anafthdev.musicompose2.data.SongAction
import com.anafthdev.musicompose2.foundation.service.MediaPlayerService

class MediaPlayerReceiver: BroadcastReceiver() {
	
	override fun onReceive(context: Context, intent: Intent) {
		val serviceIntent = Intent(context, MediaPlayerService::class.java)
		
		when (SongAction.values()[intent.action?.toInt() ?: SongAction.NOTHING.ordinal]) {
			SongAction.PAUSE -> {
				serviceIntent.action = SongAction.PAUSE.ordinal.toString()
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					context.startForegroundService(serviceIntent)
				} else context.startService(serviceIntent)
			}
			SongAction.RESUME -> {
				serviceIntent.action = SongAction.RESUME.ordinal.toString()
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					context.startForegroundService(serviceIntent)
				} else context.startService(serviceIntent)
			}
			SongAction.NEXT -> {
				serviceIntent.action = SongAction.NEXT.ordinal.toString()
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					context.startForegroundService(serviceIntent)
				} else context.startService(serviceIntent)
			}
			SongAction.PREVIOUS -> {
				serviceIntent.action = SongAction.PREVIOUS.ordinal.toString()
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					context.startForegroundService(serviceIntent)
				} else context.startService(serviceIntent)
			}
			SongAction.NOTHING -> {}
		}
	}
	
}