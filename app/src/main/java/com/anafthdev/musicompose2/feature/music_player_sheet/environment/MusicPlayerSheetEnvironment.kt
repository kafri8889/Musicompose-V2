package com.anafthdev.musicompose2.feature.music_player_sheet.environment

import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class MusicPlayerSheetEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher
): IMusicPlayerSheetEnvironment {



}