package com.anafthdev.musicompose2.foundation.common

import androidx.compose.runtime.compositionLocalOf
import com.anafthdev.musicompose2.data.model.Song

interface SongController {
	
	fun play(song: Song)
	
	fun resume()
	
	fun pause()
	
	fun previous()
	
	fun next()
	
	fun forward()
	
	fun backward()
	
	fun changePlaybackMode()
	
	fun snapTo(duration: Long)
	
	fun updateSong(song: Song)
	
	fun setShuffled(shuffle: Boolean)
	
	fun hideBottomMusicPlayer()
	
	fun showBottomMusicPlayer()
	
}

val LocalSongController = compositionLocalOf<SongController?> { null }
