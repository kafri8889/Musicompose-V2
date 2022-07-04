package com.anafthdev.musicompose2.feature.musicompose.environment

import com.anafthdev.musicompose2.data.SortSongOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.utils.AppUtil.collator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MusicomposeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IMusicomposeEnvironment {
	
	private val _songs = MutableStateFlow(emptyList<Song>())
	private val songs: StateFlow<List<Song>> = _songs
	
	private val _currentPlayedSong = MutableStateFlow(Song.default)
	private val currentPlayedSong: StateFlow<Song> = _currentPlayedSong
	
	private val _isPlaying = MutableStateFlow(false)
	private val isPlaying: StateFlow<Boolean> = _isPlaying
	
	private val _isBottomMusicPlayerShowed = MutableStateFlow(false)
	private val isBottomMusicPlayerShowed: StateFlow<Boolean> = _isBottomMusicPlayerShowed
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				repository.getSongs(),
				appDatastore.getSortSongOption
			) { mSongs, sortSongOption ->
				mSongs to sortSongOption
			}.collect { (mSongs, sortSongOption) ->
				val sortedSongs = when (sortSongOption) {
					SortSongOption.SONG_NAME -> mSongs.sortedWith(
						Comparator { o1, o2 ->
							return@Comparator collator.compare(o1.title, o2.title)
						}
					)
					SortSongOption.DATE_ADDED -> mSongs.sortedByDescending { it.dateAdded }
					SortSongOption.ARTIST_NAME -> mSongs.sortedBy { it.artist }
				}.distinctBy { it.audioID }
				
				sortedSongs.find { it.audioID == currentPlayedSong.value.audioID }?.let {
					_currentPlayedSong.emit(it)
				}
				
				_songs.emit(sortedSongs)
			}
		}
	}
	
	override fun getSongs(): Flow<List<Song>> {
		return songs
	}
	
	override fun getCurrentPlayedSong(): Flow<Song> {
		return currentPlayedSong
	}
	
	override fun isPlaying(): Flow<Boolean> {
		return isPlaying
	}
	
	override fun isBottomMusicPlayerShowed(): Flow<Boolean> {
		return isBottomMusicPlayerShowed
	}
	
	override suspend fun play(song: Song) {
		_currentPlayedSong.emit(song)
		// TODO: play song
	}
	
	override suspend fun pause() {
		_isPlaying.emit(false)
		// TODO: pause
	}
	
	override suspend fun resume() {
		_isPlaying.emit(true)
		// TODO: resume
	}
	
	override suspend fun setFavorite(favorite: Boolean) {
		repository.updateLocalSongs(
			currentPlayedSong.value.copy(
				isFavorite = favorite
			)
		)
	}
	
	override suspend fun setShowBottomMusicPlayer(show: Boolean) {
		_isBottomMusicPlayerShowed.emit(show)
	}
	
}