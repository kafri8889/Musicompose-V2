package com.anafthdev.musicompose2.feature.music_player_sheet.environment

import com.anafthdev.musicompose2.data.SortPlaylistOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.common.song_alarm_manager.SongAlarmManager
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.utils.AppUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.Duration

class MusicPlayerSheetEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val songAlarmManager: SongAlarmManager,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IMusicPlayerSheetEnvironment {
	
	private val _playlists = MutableStateFlow(emptyList<Playlist>())
	private val playlist: StateFlow<List<Playlist>> = _playlists
	
	private val _isTimerActive = MutableStateFlow(false)
	private val isTimerActive: StateFlow<Boolean> = _isTimerActive
	
	init {
		CoroutineScope(dispatcher).launch {
			while (true) {
				delay(2000)
				_isTimerActive.emit(songAlarmManager.isExists())
			}
		}
		
		CoroutineScope(dispatcher).launch {
			combine(
				repository.getPlaylists(),
				appDatastore.getSortPlaylistOption
			) { mPlaylists, sortPlaylistOption ->
				mPlaylists to sortPlaylistOption
			}.collect { (mPlaylists, sortPlaylistOption) ->
				val favoritePlaylist = mPlaylists.find { it.id == Playlist.favorite.id }
				val nonDefaultPlaylists = mPlaylists.filterNot { it.isDefault }
				
				val sortedPlaylist = when (sortPlaylistOption) {
					SortPlaylistOption.NUMBER_OF_SONGS -> nonDefaultPlaylists.sortedByDescending { it.songs.size }
					SortPlaylistOption.PLAYLIST_NAME -> nonDefaultPlaylists.sortedWith(
						Comparator { o1, o2 ->
							return@Comparator AppUtil.collator.compare(o1.name, o2.name)
						}
					)
				}
				
				_playlists.emit(
					sortedPlaylist.toMutableList().apply {
						favoritePlaylist?.let {
							add(0, it)
						}
					}
				)
			}
		}
	}
	
	override fun getPlaylists(): Flow<List<Playlist>> {
		return playlist
	}
	
	override fun isTimerActive(): Flow<Boolean> {
		return isTimerActive
	}
	
	override suspend fun addToPlaylist(song: Song, playlist: Playlist) {
		if (playlist.id == Playlist.favorite.id) {
			repository.updateSongs(
				song.copy(
					isFavorite = true
				)
			)
		}
		
		repository.updatePlaylists(
			playlist.copy(
				songs = playlist.songs.toMutableList().apply {
					add(song.audioID)
				}
			)
		)
	}
	
	override suspend fun setTimer(duration: Duration) {
		if (duration.inWholeSeconds == 0L) songAlarmManager.cancelTimer()
		else songAlarmManager.setTimer(duration.inWholeMilliseconds)
	}
	
}