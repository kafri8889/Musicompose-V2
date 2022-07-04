package com.anafthdev.musicompose2.data.datastore

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortSongOption
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
	
	suspend fun setSortSongOption(option: SortSongOption) {
		context.datastore.edit { preferences ->
			preferences[sortSongOption] = option.ordinal
		}
	}
	
	suspend fun setSortAlbumOption(option: SortAlbumOption) {
		context.datastore.edit { preferences ->
			preferences[sortAlbumOption] = option.ordinal
		}
	}
	
	suspend fun setSortArtistOption(option: SortArtistOption) {
		context.datastore.edit { preferences ->
			preferences[sortArtistOption] = option.ordinal
		}
	}
	
	val getLanguage: Flow<Language> = context.datastore.data.map { preferences ->
		Language.values()[preferences[language] ?: Language.INDONESIAN.ordinal]
	}
	
	val getUiMode: Flow<UiMode> = context.datastore.data.map { preferences ->
		UiMode.values()[preferences[uiMode] ?: UiMode.LIGHT.ordinal]
	}
	
	val getSortSongOption: Flow<SortSongOption> = context.datastore.data.map { preferences ->
		SortSongOption.values()[preferences[sortSongOption] ?: SortSongOption.SONG_NAME.ordinal]
	}
	
	val getSortAlbumOption: Flow<SortAlbumOption> = context.datastore.data.map { preferences ->
		SortAlbumOption.values()[preferences[sortAlbumOption] ?: SortAlbumOption.ALBUM_NAME.ordinal]
	}
	
	val getSortArtistOption: Flow<SortArtistOption> = context.datastore.data.map { preferences ->
		SortArtistOption.values()[preferences[sortArtistOption] ?: SortArtistOption.ARTIST_NAME.ordinal]
	}
	
	companion object {
		val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_datastore")
		
		val language = intPreferencesKey(Preference.LANGUAGE)
		val uiMode = intPreferencesKey(Preference.UI_MODE)
		val sortSongOption = intPreferencesKey(Preference.SORT_SONG_OPTION)
		val sortAlbumOption = intPreferencesKey(Preference.SORT_ALBUM_OPTION)
		val sortArtistOption = intPreferencesKey(Preference.SORT_ARTIST_OPTION)
		
	}
}

val LocalAppDatastore = compositionLocalOf<AppDatastore?> { null }
