package com.anafthdev.musicompose2.foundation.uimode.di

import com.anafthdev.musicompose2.foundation.uimode.environment.IUiModeEnvironment
import com.anafthdev.musicompose2.foundation.uimode.environment.UiModeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UiModeModule {
	
	@Binds
	abstract fun provideEnvironment(
		uiModeEnvironment: UiModeEnvironment
	): IUiModeEnvironment
	
}