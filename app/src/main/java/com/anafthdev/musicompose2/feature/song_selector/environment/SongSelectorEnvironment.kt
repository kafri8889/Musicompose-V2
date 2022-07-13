package com.anafthdev.musicompose2.feature.song_selector.environment

import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.common.Quad
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.foundation.extension.isNotDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class SongSelectorEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): ISongSelectorEnvironment {
	
	private val _query = MutableStateFlow("")
	private val query: StateFlow<String> = _query
	
	private val _playlist = MutableStateFlow(Playlist.default)
	private val playlist: StateFlow<Playlist> = _playlist
	
	private val _songs = MutableStateFlow(emptyList<Song>())
	private val songs: StateFlow<List<Song>> = _songs
	
	private val _selectedSong = MutableStateFlow(emptyList<Song>())
	private val selectedSong: StateFlow<List<Song>> = _selectedSong
	
	private val _songSelectorType = MutableStateFlow(SongSelectorType.ADD_SONG)
	private val songSelectorType: StateFlow<SongSelectorType> = _songSelectorType
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				query,
				playlist,
				repository.getSongs(),
				repository.getPlaylists()
			) { mQuery, mPlaylist, mSongs, mPlaylists  ->
				Quad(mQuery, mPlaylist, mSongs, mPlaylists)
			}.collect { (mQuery, mPlaylist, mSongs, mPlaylists) ->
				val filteredSongs = mSongs.filter {
					it.displayName.contains(mQuery, true) or it.title.contains(mQuery, true)
				}
				
				mPlaylists.find { it.id == mPlaylist.id }?.let { nPlaylist ->
					val songList = mPlaylist.songs.map { songID ->
						mSongs.find { it.audioID == songID } ?: Song.default
					}.filter { it.isNotDefault() }
					
					_playlist.emit(nPlaylist)
					_selectedSong.emit(songList)
				}
				
				_songs.emit(filteredSongs)
			}
		}
	}
	
	override fun getSearchQuery(): Flow<String> {
		return query
	}
	
	override fun getSelectedSong(): Flow<List<Song>> {
		return selectedSong
	}
	
	override fun getSongs(): Flow<List<Song>> {
		return songs
	}
	
	override suspend fun search(q: String) {
		_query.emit(q)
	}
	
	override suspend fun addSong(song: Song) {
		val mSelectedSong = selectedSong.value.toMutableList().apply {
			add(
				song.copy(
					isFavorite = if (songSelectorType.value == SongSelectorType.ADD_FAVORITE_SONG) true
					else song.isFavorite
				)
			)
		}.distinctBy { it.audioID }
		
		_selectedSong.emit(mSelectedSong)
		repository.updateSongs(*mSelectedSong.toTypedArray())
		repository.updatePlaylists(
			playlist.value.copy(
				songs = mSelectedSong.map { it.audioID }
			)
		)
	}
	
	override suspend fun removeSong(song: Song) {
		val mSelectedSong = selectedSong.value.toMutableList().apply {
			removeIf { it.audioID == song.audioID }
		}.distinctBy { it.audioID }
		
		Timber.i("selektet: $mSelectedSong")
		
		if (songSelectorType.value == SongSelectorType.ADD_FAVORITE_SONG) repository.updateSongs(
			song.copy(
				isFavorite = false
			)
		)
		
		_selectedSong.emit(mSelectedSong)
		
		repository.updatePlaylists(
			playlist.value.copy(
				songs = mSelectedSong.map { it.audioID }
			)
		)
	}
	
	override suspend fun getPlaylist(playlistID: Int) {
		val mPlaylist = repository.getPlaylist(playlistID)
		
		if (mPlaylist != null) {
			_playlist.emit(mPlaylist)
		}
	}
	
	override suspend fun setSongSelectorType(type: SongSelectorType) {
		_songSelectorType.emit(type)
	}
}