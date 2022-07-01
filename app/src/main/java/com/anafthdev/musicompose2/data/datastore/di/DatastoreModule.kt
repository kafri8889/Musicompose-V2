package com.anafthdev.musicompose2.data.datastore.di

import android.content.Context
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
	
	@Provides
	fun provideAppDatastore(
		@ApplicationContext context: Context
	): AppDatastore = AppDatastore(context)
}