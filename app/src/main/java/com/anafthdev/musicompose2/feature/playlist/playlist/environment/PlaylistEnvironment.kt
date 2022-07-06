package com.anafthdev.musicompose2.feature.playlist.playlist.environment

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class PlaylistEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IPlaylistEnvironment {
	
	private val _songs = MutableStateFlow(emptyList<Song>())
	private val songs: StateFlow<List<Song>> = _songs
	
	private val _playlist = MutableStateFlow(Playlist.default)
	private val playlist: StateFlow<Playlist> = _playlist
	
	private val _playlistID = MutableStateFlow(Playlist.default.id)
	private val playlistID: StateFlow<Int> = _playlistID
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				playlistID,
				repository.getPlaylists(),
				repository.getSongs()
			) { mPlaylistID, playlists, mSongs ->
				Triple(mPlaylistID, playlists, mSongs)
			}.collect { (mPlaylistID, playlists, mSongs) ->
				var mPlaylist = playlists.find { it.id == mPlaylistID } ?: Playlist.default
				
				if (mPlaylist.id == Playlist.justPlayed.id) {
					mPlaylist = mPlaylist.copy(
						songs = mPlaylist.songs.reversed()
					)
				}
				
				val songList = mPlaylist.songs.map { songID ->
					mSongs.find { it.audioID == songID } ?: Song.default
				}.filterNot { it.audioID == Song.default.audioID }
				
				_songs.emit(songList)
				_playlist.emit(mPlaylist)
			}
		}
	}
	
	override fun getSongs(): Flow<List<Song>> {
		return songs
	}
	
	override fun getPlaylist(): Flow<Playlist> {
		return playlist
	}
	
	override suspend fun setPlaylist(playlistID: Int) {
		_playlistID.emit(playlistID)
	}
	
	override suspend fun updatePlaylist(mPlaylist: Playlist) {
		repository.updatePlaylists(mPlaylist)
	}
	
}