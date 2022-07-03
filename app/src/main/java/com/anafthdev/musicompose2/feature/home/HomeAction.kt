package com.anafthdev.musicompose2.feature.home

import com.anafthdev.musicompose2.data.model.Song

sealed interface HomeAction {
	data class UpdateSong(val song: Song): HomeAction
}