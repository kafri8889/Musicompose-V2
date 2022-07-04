package com.anafthdev.musicompose2.data.datastore

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anafthdev.musicompose2.data.preference.Language
import com.anafthdev.musicompose2.data.preference.Preference
import com.anafthdev.musicompose2.foundation.uimode.data.UiMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDatastore @Inject constructor(private val context: Context) {
	
	suspend fun setLanguage(lang: Language) {
		context.datastore.edit { preferences ->
			preferences[language] = lang.ordinal
		}
	}
	
	suspend fun setUiMode(mUiMode: UiMode) {
		context.datastore.edit { preferences ->
			preferences[uiMode] = mUiMode.ordinal
		}
	}
	
	val getLanguage: Flow<Language> = context.datastore.data.map { preferences ->
		Language.values()[preferences[language] ?: Language.INDONESIAN.ordinal]
	}
	
	val getUiMode: Flow<UiMode> = context.datastore.data.map { preferences ->
		UiMode.values()[preferences[uiMode] ?: UiMode.LIGHT.ordinal]
	}
	
	companion object {
		val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_datastore")
		
		val language = intPreferencesKey(Preference.LANGUAGE)
		val uiMode = intPreferencesKey(Preference.UI_MODE)
		
	}
}

val LocalAppDatastore = compositionLocalOf<AppDatastore?> { null }
