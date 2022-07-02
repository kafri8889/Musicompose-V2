package com.anafthdev.musicompose2.foundation.common

import androidx.compose.runtime.compositionLocalOf
import com.anafthdev.musicompose2.data.model.Song

interface SongController {
	
	fun play(song: Song)
	
	fun resume()
	
	fun pause()
	
}

val LocalSongController = compositionLocalOf<SongController?> { null }
