package com.anafthdev.musicompose2.foundation.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.view.KeyEvent
import com.anafthdev.musicompose2.data.SongAction
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeState
import com.anafthdev.musicompose2.foundation.common.SongController
import com.anafthdev.musicompose2.utils.NotificationUtil

class MediaPlayerService: Service() {
	
	private lateinit var mediaSession: MediaSession
	private lateinit var mediaStyle: Notification.MediaStyle
	private lateinit var notificationManager: NotificationManager
	
	private val binder: IBinder = MediaPlayerServiceBinder()
	
	private var songController: SongController? = null
	
	private var isForegroundService = false
	
	override fun onCreate() {
		super.onCreate()
		
		notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		mediaSession = MediaSession(this, "MediaPlayerSessionService")
		mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)
		
		mediaSession.setCallback(object : MediaSession.Callback() {
			override fun onMediaButtonEvent(mediaButtonIntent: Intent): Boolean {
				
				if (Intent.ACTION_MEDIA_BUTTON == mediaButtonIntent.action) {
					val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
						mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
					} else mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
					
					event?.let {
						when (it.keyCode) {
							KeyEvent.KEYCODE_MEDIA_PLAY -> songController?.resume()
							KeyEvent.KEYCODE_MEDIA_PAUSE -> songController?.pause()
							KeyEvent.KEYCODE_MEDIA_NEXT -> songController?.next()
							KeyEvent.KEYCODE_MEDIA_PREVIOUS -> songController?.previous()
							else -> {}
						}
					}
				}
				
				return true
			}
		})
		
		startForeground(123, NotificationUtil.foregroundNotification(this)).also {
			isForegroundService = true
		}
	}
	
	override fun onBind(p0: Intent?): IBinder {
		return binder
	}
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		when (SongAction.values()[intent?.action?.toInt() ?: SongAction.NOTHING.ordinal]) {
			SongAction.PAUSE -> songController?.pause()
			SongAction.RESUME -> songController?.resume()
			SongAction.NEXT -> songController?.next()
			SongAction.PREVIOUS -> songController?.previous()
			SongAction.NOTHING -> {}
		}
		
		val musicomposeState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent?.getParcelableExtra("musicomposeState", MusicomposeState::class.java)
		} else intent?.getParcelableExtra("musicomposeState") as? MusicomposeState
		
		musicomposeState?.let { newState ->
			if (isForegroundService) {
				
				mediaSession.setPlaybackState(
					PlaybackState.Builder()
						.setState(
							if (newState.isPlaying) PlaybackState.STATE_PLAYING else PlaybackState.STATE_PAUSED,
							newState.currentDuration,
							1f
						)
						.setActions(PlaybackState.ACTION_PLAY_PAUSE)
						.build()
				)
				
				mediaSession.setMetadata(
					MediaMetadata.Builder()
						.putString(MediaMetadata.METADATA_KEY_TITLE, newState.currentSongPlayed.title)
						.putString(MediaMetadata.METADATA_KEY_ALBUM, newState.currentSongPlayed.album)
						.putString(MediaMetadata.METADATA_KEY_ARTIST, newState.currentSongPlayed.artist)
						.putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, newState.currentSongPlayed.albumPath)
						.putLong(MediaMetadata.METADATA_KEY_DURATION, newState.currentSongPlayed.duration)
						.build()
				)
				
				notificationManager.notify(
					0,
					NotificationUtil.notificationMediaPlayer(
						applicationContext,
						Notification.MediaStyle().setMediaSession(mediaSession.sessionToken),
						newState
					)
				)
			}
		}
		
		return START_NOT_STICKY
	}
	
	override fun onTaskRemoved(rootIntent: Intent?) {
		mediaSession.isActive = false
		mediaSession.release()
		notificationManager.cancelAll()
		songController?.stop()
		stopForeground(STOP_FOREGROUND_REMOVE).also {
			isForegroundService = false
		}
	}
	
	override fun onLowMemory() {
		super.onLowMemory()
		songController?.stop()
	}
	
	fun setSongController(controller: SongController) {
		songController = controller
	}
	
	inner class MediaPlayerServiceBinder: Binder() {
		
		fun getService(): MediaPlayerService {
			return this@MediaPlayerService
		}
	}
	
}