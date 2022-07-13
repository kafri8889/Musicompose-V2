package com.anafthdev.musicompose2.foundation.common.song_alarm_manager.di

import android.content.Context
import com.anafthdev.musicompose2.foundation.common.song_alarm_manager.SongAlarmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SongAlarmManagerModule {
	
	@Provides
	@Singleton
	fun provideSongAlarmManager(
		@ApplicationContext context: Context
	): SongAlarmManager = SongAlarmManager(context)
	
}