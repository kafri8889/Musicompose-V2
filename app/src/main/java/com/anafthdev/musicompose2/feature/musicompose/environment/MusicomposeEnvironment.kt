package com.anafthdev.musicompose2.feature.musicompose.environment

import com.anafthdev.musicompose2.data.SortSongOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.utils.AppUtil.collator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
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
	
	private val _currentDuration = MutableStateFlow(0L)
	private val currentDuration: StateFlow<Long> = _currentDuration
	
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
	
	override fun getCurrentDuration(): Flow<Long> {
		return currentDuration
	}
	
	override fun isBottomMusicPlayerShowed(): Flow<Boolean> {
		return isBottomMusicPlayerShowed
	}
	
	override suspend fun play(song: Song) {
		val justPlayedPlaylist = repository.getPlaylist(Playlist.justPlayed.id)
		justPlayedPlaylist?.let { playlist ->
			repository.updatePlaylists(
				playlist.copy(
					songs = playlist.songs.toMutableList().apply {
						val contain = playlist.songs.find {
							it == song.audioID
						} != null
						
						if (contain) removeIf { it == song.audioID }
						
						if (playlist.songs.size < 10) add(song.audioID)
						else {
							removeAt(0)
							add(song.audioID)
						}
					}
				)
			)
		}
		
		_currentPlayedSong.emit(song)
		
		appDatastore.setLastSongPlayed(song.audioID)
		
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
	
	override suspend fun previous() {
		// TODO: previous 
	}
	
	override suspend fun next() {
		// TODO: next 
	}
	
	override suspend fun snapTo(duration: Long) {
		_currentDuration.emit(duration)
		// TODO: snap to duration
	}
	
	override suspend fun updateSong(song: Song) {
		repository.updateSongs(song)
		
		val favoritePlaylist = repository.getPlaylist(Playlist.favorite.id)
		favoritePlaylist?.let { playlist ->
			repository.updatePlaylists(
				playlist.copy(
					songs = playlist.songs.toMutableList().apply {
						if (song.isFavorite) add(song.audioID)
						else removeIf { it == song.audioID }
					}
				)
			)
		}
		
		val justPlayedPlaylist = repository.getPlaylist(Playlist.justPlayed.id)
		justPlayedPlaylist?.let { playlist ->
			repository.updatePlaylists(
				playlist.copy(
					songs = playlist.songs.toMutableList().apply {
						// update song in justPlayed playlist
						
						val songIndex = indexOfFirst { it == song.audioID }
						
						if (songIndex != -1) set(songIndex, song.audioID)
					}
				)
			)
		}
	}
	
	override suspend fun setShowBottomMusicPlayer(show: Boolean) {
		_isBottomMusicPlayerShowed.emit(show)
	}
	
	override suspend fun playLastSongPlayed() {
		appDatastore.getLastSongPlayed.firstOrNull()?.let { audioID ->
			repository.getLocalSong(audioID)?.let { song ->
				_currentPlayedSong.emit(song)
			}
		}
	}
	
}