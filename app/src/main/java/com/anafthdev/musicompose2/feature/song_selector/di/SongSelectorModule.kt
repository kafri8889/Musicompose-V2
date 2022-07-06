package com.anafthdev.musicompose2.feature.song_selector.di

import com.anafthdev.musicompose2.feature.song_selector.environment.ISongSelectorEnvironment
import com.anafthdev.musicompose2.feature.song_selector.environment.SongSelectorEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SongSelectorModule {
	
	@Binds
	abstract fun provideEnvironment(
		songSelectorEnvironment: SongSelectorEnvironment
	): ISongSelectorEnvironment
	
}