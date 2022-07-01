package com.anafthdev.musicompose2.data.datasource.di

import com.anafthdev.musicompose2.data.datasource.local.LocalDatasource
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {
	
	@Provides
	@Singleton
	fun provideLocalDatasource(
		songDao: SongDao
	): LocalDatasource = LocalDatasource(songDao)
	
}