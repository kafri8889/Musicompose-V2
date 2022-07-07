package com.anafthdev.musicompose2.feature.music_player_sheet.di

import com.anafthdev.musicompose2.feature.music_player_sheet.environment.IMusicPlayerSheetEnvironment
import com.anafthdev.musicompose2.feature.music_player_sheet.environment.MusicPlayerSheetEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MusicPlayerSheetModule {
	
	@Binds
	abstract fun provideEnvironment(
		musicPlayerSheetEnvironment: MusicPlayerSheetEnvironment
	): IMusicPlayerSheetEnvironment
	
}