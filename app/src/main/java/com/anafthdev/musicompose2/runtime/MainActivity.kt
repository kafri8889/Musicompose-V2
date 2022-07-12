package com.anafthdev.musicompose2.runtime

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.anafthdev.musicompose2.BuildConfig
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.feature.musicompose.Musicompose
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeAction
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeViewModel
import com.anafthdev.musicompose2.foundation.common.SongController
import com.anafthdev.musicompose2.foundation.localized.LocalizedActivity
import com.anafthdev.musicompose2.foundation.localized.data.OnLocaleChangedListener
import com.anafthdev.musicompose2.foundation.service.MediaPlayerService
import com.anafthdev.musicompose2.utils.SongUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: LocalizedActivity(), ServiceConnection {
	
	@Inject lateinit var appDatastore: AppDatastore
	@Inject lateinit var repository: Repository
	
	private val musicomposeViewModel: MusicomposeViewModel by viewModels()
	
	private var mediaPlayerService: MediaPlayerService? = null
	
	private val songController = object: SongController {
		override fun play(song: Song) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Play(song)
			)
		}
		
		override fun resume() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetPlaying(
					isPlaying = true
				)
			)
		}
		
		override fun pause() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetPlaying(
					isPlaying = false
				)
			)
		}
		
		override fun stop() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Stop
			)
		}
		
		override fun previous() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Previous
			)
		}
		
		override fun next() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Next
			)
		}
		
		override fun forward() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Forward
			)
		}
		
		override fun backward() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Backward
			)
		}
		
		override fun changePlaybackMode() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.ChangePlaybackMode
			)
		}
		
		override fun snapTo(duration: Long) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SnapTo(
					duration = duration
				)
			)
		}
		
		override fun updateSong(song: Song) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.UpdateSong(
					song = song
				)
			)
		}
		
		override fun playAll(songs: List<Song>) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.PlayAll(
					songs = songs
				)
			)
		}
		
		override fun updateQueueSong(songs: List<Song>) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.UpdateQueueSong(
					songs = songs
				)
			)
		}
		
		override fun setShuffled(shuffle: Boolean) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShuffle(
					isShuffled = shuffle
				)
			)
		}
		
		override fun hideBottomMusicPlayer() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShowBottomMusicPlayer(
					isShowed = false
				)
			)
		}
		
		override fun showBottomMusicPlayer() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShowBottomMusicPlayer(
					isShowed = true
				)
			)
		}
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (BuildConfig.DEBUG) Timber.plant(object : Timber.DebugTree() {
			override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
				super.log(priority, "DEBUG_$tag", message, t)
			}
		})
		
		WindowCompat.setDecorFitsSystemWindows(window, false)
		
		setListener(object: OnLocaleChangedListener {
			override fun onChanged() {
				lifecycleScope.launch {
					repository.updatePlaylist(Playlist.favorite.id, getString(R.string.favorite))
					repository.updatePlaylist(Playlist.justPlayed.id, getString(R.string.just_played))
				}
			}
		})
		
		val serviceIntent = Intent(this, MediaPlayerService::class.java)
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			startForegroundService(serviceIntent)
		} else startService(serviceIntent)
		
		bindService(
			serviceIntent,
			this,
			BIND_AUTO_CREATE
		)
		
		setContent {
			Musicompose(
				appDatastore = appDatastore,
				songController = songController,
				viewModel = musicomposeViewModel
			)
		}
	}
	
	override fun onResume() {
		super.onResume()
		
		lifecycleScope.launch {
			val songs = SongUtil.getSong(this@MainActivity).toTypedArray()
			
			repository.insertSongs(*songs)
			repository.insertPlaylists(
				Playlist.favorite,
				Playlist.justPlayed
			)
			
			repository.updatePlaylist(Playlist.favorite.id, getString(R.string.favorite))
			repository.updatePlaylist(Playlist.justPlayed.id, getString(R.string.just_played))
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
		
		try {
			unbindService(this)
		} catch (e: IllegalArgumentException) {
			Timber.e(e, "Service not registered")
		}
	}
	
	override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
		val binder = p1 as MediaPlayerService.MediaPlayerServiceBinder
		
		mediaPlayerService = binder.getService()
		mediaPlayerService!!.setSongController(songController)
	}
	
	override fun onServiceDisconnected(p0: ComponentName?) {
		mediaPlayerService = null
	}
	
}
