package com.anafthdev.musicompose2.feature.musicompose.environment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.anafthdev.musicompose2.data.PlaybackMode
import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.data.SortSongOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.foundation.extension.isNotDefault
import com.anafthdev.musicompose2.foundation.extension.toast
import com.anafthdev.musicompose2.utils.AppUtil.collator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnsafeOptInUsageError")
class MusicomposeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IMusicomposeEnvironment {
	
	private val _songs = MutableStateFlow(emptyList<Song>())
	private val songs: StateFlow<List<Song>> = _songs
	
	private val _currentSongQueue = MutableStateFlow(emptyList<Song>())
	private val currentSongQueue: StateFlow<List<Song>> = _currentSongQueue
	
	private val _currentPlayedSong = MutableStateFlow(Song.default)
	private val currentPlayedSong: StateFlow<Song> = _currentPlayedSong
	
	private val _currentDuration = MutableStateFlow(0L)
	private val currentDuration: StateFlow<Long> = _currentDuration
	
	private val _skipForwardBackward = MutableStateFlow(SkipForwardBackward.FIVE_SECOND)
	private val skipForwardBackward: StateFlow<SkipForwardBackward> = _skipForwardBackward
	
	private val _playbackMode = MutableStateFlow(PlaybackMode.REPEAT_ALL)
	private val playbackMode: StateFlow<PlaybackMode> = _playbackMode
	
	private val _isPlaying = MutableStateFlow(false)
	private val isPlaying: StateFlow<Boolean> = _isPlaying
	
	private val _isShuffled = MutableStateFlow(false)
	private val isShuffled: StateFlow<Boolean> = _isShuffled
	
	private val _isBottomMusicPlayerShowed = MutableStateFlow(false)
	private val isBottomMusicPlayerShowed: StateFlow<Boolean> = _isBottomMusicPlayerShowed
	
	private var songRunnable: Runnable = Runnable {}
	private var songHandler: Handler = Handler(Looper.getMainLooper())
	private var playerHandler: Handler = Handler(Looper.getMainLooper())
	
	private val exoPlayer = ExoPlayer.Builder(context).build().apply {
		addListener(object : Player.Listener {
			override fun onPlaybackStateChanged(playbackState: Int) {
				super.onPlaybackStateChanged(playbackState)
				if (playbackState == ExoPlayer.STATE_ENDED) {
					when (playbackMode.value) {
						PlaybackMode.REPEAT_ALL -> {
							val currentIndex = currentSongQueue.value.indexOfFirst {
								it.audioID == currentPlayedSong.value.audioID
							}
							
							val nextSong = when {
								currentIndex == currentSongQueue.value.lastIndex -> currentSongQueue.value[0]
								currentIndex != -1 -> currentSongQueue.value[currentIndex + 1]
								else -> currentSongQueue.value[0]
							}
							
							CoroutineScope(dispatcher).launch { play(nextSong) }
						}
						PlaybackMode.REPEAT_OFF -> {
							this@apply.stop()
						}
						PlaybackMode.REPEAT_ONE -> {
							CoroutineScope(dispatcher).launch { play(currentPlayedSong.value) }
						}
					}
				}
				
			}
			
			override fun onIsPlayingChanged(isPlaying: Boolean) {
				super.onIsPlayingChanged(isPlaying)
				_isPlaying.tryEmit(isPlaying)
			}
		})
	}
	
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
				
				// TODO: set song queue
				_currentSongQueue.emit(sortedSongs)
			}
		}
		
		CoroutineScope(dispatcher).launch {
			combine(
				appDatastore.getPlaybackMode,
				appDatastore.getSkipForwardBackward
			) { mPlaybackMode, mSkipForwardBackward ->
				mPlaybackMode to mSkipForwardBackward
			}.collect { (mPlaybackMode, mSkipForwardBackward) ->
				_playbackMode.emit(mPlaybackMode)
				_skipForwardBackward.emit(mSkipForwardBackward)
			}
		}
	}
	
	override fun getSongs(): Flow<List<Song>> {
		return songs
	}
	
	override fun getCurrentPlayedSong(): Flow<Song> {
		return currentPlayedSong
	}
	
	override fun getSkipForwardBackward(): Flow<SkipForwardBackward> {
		return skipForwardBackward
	}
	
	override fun getPlaybackMode(): Flow<PlaybackMode> {
		return playbackMode
	}
	
	override fun isPlaying(): Flow<Boolean> {
		return isPlaying
	}
	
	override fun isShuffled(): Flow<Boolean> {
		return isShuffled
	}
	
	override fun getCurrentDuration(): Flow<Long> {
		return currentDuration
	}
	
	override fun getCurrentSongQueue(): Flow<List<Song>> {
		return currentSongQueue
	}
	
	override fun isBottomMusicPlayerShowed(): Flow<Boolean> {
		return isBottomMusicPlayerShowed
	}
	
	override fun snapTo(duration: Long, fromUser: Boolean) {
		_currentDuration.tryEmit(duration)
		
		if (fromUser) playerHandler.post { exoPlayer.seekTo(duration) }
	}
	
	override suspend fun play(song: Song) {
		if (song.isNotDefault()) {
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
			song.title.toast(context)
			_currentPlayedSong.emit(song)
			
			appDatastore.setLastSongPlayed(song.audioID)
			
			playerHandler.post {
				exoPlayer.setMediaItem(MediaItem.fromUri(song.path.toUri()))
				exoPlayer.prepare()
				exoPlayer.play()
			}
			
			songRunnable = Runnable {
				snapTo(
					duration = if (exoPlayer.duration != -1L) exoPlayer.currentPosition else 0L,
					fromUser = false
				)
				
				songHandler.postDelayed(songRunnable, 1000)
			}
			
			songHandler.post(songRunnable)
		}
	}
	
	override suspend fun pause() {
		playerHandler.post { exoPlayer.pause() }
	}
	
	override suspend fun resume() {
		playerHandler.post { exoPlayer.play() }
	}
	
	override suspend fun previous() {
		val currentIndex = currentSongQueue.value.indexOfFirst {
			it.audioID == currentPlayedSong.value.audioID
		}
		
		val previousSong = when {
			currentIndex == 0 -> currentSongQueue.value[currentSongQueue.value.lastIndex]
			currentIndex >= 1 -> currentSongQueue.value[currentIndex - 1]
			else -> currentSongQueue.value[0]
		}
		
		CoroutineScope(dispatcher).launch { play(previousSong) }
	}
	
	override suspend fun next() {
		val currentIndex = currentSongQueue.value.indexOfFirst {
			it.audioID == currentPlayedSong.value.audioID
		}
		
		val nextSong = when {
			currentIndex == currentSongQueue.value.lastIndex -> currentSongQueue.value[0]
			currentIndex != -1 -> currentSongQueue.value[currentIndex + 1]
			else -> currentSongQueue.value[0]
		}
		
		CoroutineScope(dispatcher).launch { play(nextSong) }
	}
	
	override suspend fun forward() {
		playerHandler.post {
			snapTo(
				duration = when (skipForwardBackward.value) {
					SkipForwardBackward.FIVE_SECOND -> exoPlayer.currentPosition + 5.seconds.inWholeMilliseconds
					SkipForwardBackward.TEN_SECOND -> exoPlayer.currentPosition + 10.seconds.inWholeMilliseconds
					SkipForwardBackward.FIFTEEN_SECOND -> exoPlayer.currentPosition + 15.seconds.inWholeMilliseconds
				}
			)
		}
	}
	
	override suspend fun backward() {
		playerHandler.post {
			snapTo(
				duration = when (skipForwardBackward.value) {
					SkipForwardBackward.FIVE_SECOND -> exoPlayer.currentPosition - 5.seconds.inWholeMilliseconds
					SkipForwardBackward.TEN_SECOND -> exoPlayer.currentPosition - 10.seconds.inWholeMilliseconds
					SkipForwardBackward.FIFTEEN_SECOND -> exoPlayer.currentPosition - 15.seconds.inWholeMilliseconds
				}
			)
		}
	}
	
	override suspend fun changePlaybackMode() {
		appDatastore.setPlaybackMode(
			when (playbackMode.value) {
				PlaybackMode.REPEAT_ALL -> PlaybackMode.REPEAT_ONE
				PlaybackMode.REPEAT_ONE -> PlaybackMode.REPEAT_OFF
				PlaybackMode.REPEAT_OFF -> PlaybackMode.REPEAT_ALL
			}
		)
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
	
	override suspend fun setShuffle(shuffle: Boolean) {
		_isShuffled.emit(shuffle)
	}
	
	override suspend fun setShowBottomMusicPlayer(show: Boolean) {
		_isBottomMusicPlayerShowed.emit(show)
	}
	
	override suspend fun playLastSongPlayed() {
		appDatastore.getLastSongPlayed.firstOrNull()?.let { audioID ->
			repository.getLocalSong(audioID)?.let { song ->
				play(song)
			}
		}
	}
	
}