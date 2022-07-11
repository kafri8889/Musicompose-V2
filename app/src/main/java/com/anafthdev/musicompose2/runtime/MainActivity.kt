package com.anafthdev.musicompose2.runtime

import android.os.Bundle
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
import com.anafthdev.musicompose2.utils.SongUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: LocalizedActivity() {
	
	@Inject lateinit var appDatastore: AppDatastore
	@Inject lateinit var repository: Repository
	
	private val musicomposeViewModel: MusicomposeViewModel by viewModels()
	
	private val songController = object: SongController {
		override fun play(song: Song) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.Play(song)
			)
		}
		
		override fun resume() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetPlaying(true)
			)
		}
		
		override fun pause() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetPlaying(false)
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
				MusicomposeAction.UpdateSong(song)
			)
		}
		
		override fun updateQueueSong(songs: List<Song>) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.UpdateQueueSong(songs)
			)
		}
		
		override fun setShuffled(shuffle: Boolean) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShuffle(shuffle)
			)
		}
		
		override fun hideBottomMusicPlayer() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShowBottomMusicPlayer(false)
			)
		}
		
		override fun showBottomMusicPlayer() {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetShowBottomMusicPlayer(true)
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
		
		this.setListener(object: OnLocaleChangedListener {
			override fun onChanged() {
				lifecycleScope.launch {
					repository.updatePlaylist(Playlist.favorite.id, getString(R.string.favorite))
					repository.updatePlaylist(Playlist.justPlayed.id, getString(R.string.just_played))
				}
			}
		})
		
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
	
}
