package com.anafthdev.musicompose2.feature.music_player_sheet.environment

import kotlinx.coroutines.CoroutineDispatcher

interface IMusicPlayerSheetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
}