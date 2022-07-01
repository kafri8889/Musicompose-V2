package com.anafthdev.musicompose2.foundation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
	
	@Provides
	@Named(DiName.IO)
	fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
	
	@Provides
	@Named(DiName.MAIN)
	fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
	
}