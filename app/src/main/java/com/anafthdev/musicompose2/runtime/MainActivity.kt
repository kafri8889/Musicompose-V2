package com.anafthdev.musicompose2.runtime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.anafthdev.musicompose2.BuildConfig
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.Musicompose
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeAction
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeViewModel
import com.anafthdev.musicompose2.foundation.common.SongController
import com.anafthdev.musicompose2.foundation.localized.LocalizedActivity
import com.anafthdev.musicompose2.utils.SongUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: LocalizedActivity() {
	
	@Inject lateinit var appDatastore: AppDatastore
	@Inject lateinit var songDao: SongDao
	
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
		
		override fun setFavorite(favorite: Boolean) {
			musicomposeViewModel.dispatch(
				MusicomposeAction.SetFavorite(favorite)
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
		Timber.i("onstar")
		
		lifecycleScope.launch {
			val songs = SongUtil.getSong(this@MainActivity).toTypedArray()
			songDao.insert(*songs)
			
			Timber.i("get song from device: ${songs.contentToString()}")
		}
	}
}
