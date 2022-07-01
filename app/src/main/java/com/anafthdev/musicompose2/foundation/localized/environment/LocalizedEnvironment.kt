package com.anafthdev.musicompose2.foundation.localized.environment

import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.preference.Language
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class LocalizedEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): ILocalizedEnvironment {
	
	override suspend fun setLanguage(lang: Language) {
		appDatastore.setLanguage(lang)
	}
	
	override fun getLanguage(): Flow<Language> {
		return appDatastore.getLanguage
	}
	
	
}