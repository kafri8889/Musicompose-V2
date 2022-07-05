package com.anafthdev.musicompose2.data.datasource.di

import android.content.Context
import com.anafthdev.musicompose2.data.datasource.local.db.MusicomposeDatabase
import com.anafthdev.musicompose2.data.datasource.local.db.playlist.PlaylistDao
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
	
	@Provides
	@Singleton
	fun provideSongDao(@ApplicationContext context: Context): SongDao {
		return MusicomposeDatabase.getInstance(context).songDao()
	}
	
	@Provides
	@Singleton
	fun providePlaylistDao(@ApplicationContext context: Context): PlaylistDao {
		return MusicomposeDatabase.getInstance(context).playlistDao()
	}
	
}