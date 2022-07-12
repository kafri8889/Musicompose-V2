package com.anafthdev.musicompose2.foundation.common

import androidx.compose.runtime.compositionLocalOf
import com.anafthdev.musicompose2.data.PlaybackMode
import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.data.model.Song

interface SongController {
	
	/**
	 * Play song
	 */
	fun play(song: Song)
	
	/**
	 * Resume paused song
	 */
	fun resume()
	
	/**
	 * Pause played song
	 */
	fun pause()
	
	/**
	 * Play previous song
	 */
	fun previous()
	
	/**
	 * Play next song
	 */
	fun next()
	
	/**
	 * Skip forward, see [SkipForwardBackward]
	 */
	fun forward()
	
	/**
	 * Skip backward, see [SkipForwardBackward]
	 */
	fun backward()
	
	/**
	 * change playback mode [PlaybackMode]
	 */
	fun changePlaybackMode()
	
	/**
	 * Seeks to a position specified in milliseconds in the current song played
	 */
	fun snapTo(duration: Long)
	
	/**
	 * Update song
	 */
	fun updateSong(song: Song)
	
	/**
	 * Play all songs from the given parameters, this will also replace the current song queue
	 */
	fun playAll(songs: List<Song>)
	
	/**
	 * Replace current song queue with given parameters
	 */
	fun updateQueueSong(songs: List<Song>)
	
	/**
	 * Set whether the song queue should be shuffled or not
	 */
	fun setShuffled(shuffle: Boolean)
	
	/**
	 * Hide bottom music player
	 */
	fun hideBottomMusicPlayer()
	
	/**
	 * Show bottom music player
	 */
	fun showBottomMusicPlayer()
	
}

val LocalSongController = compositionLocalOf<SongController?> { null }
