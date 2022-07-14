package com.anafthdev.musicompose2.data.datastore

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.anafthdev.musicompose2.data.*
import com.anafthdev.musicompose2.data.model.Song
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
	
	suspend fun setSortPlaylistOption(option: SortPlaylistOption) {
		context.datastore.edit { preferences ->
			preferences[sortPlaylistOption] = option.ordinal
		}
	}
	
	suspend fun setLastSongPlayed(audioID: Long) {
		context.datastore.edit { preferences ->
			preferences[lastSongPlayed] = audioID
		}
	}
	
	suspend fun setPlaybackMode(mode: PlaybackMode) {
		context.datastore.edit { preferences ->
			preferences[playbackMode] = mode.ordinal
		}
	}
	
	suspend fun setSkipForwardBackward(duration: SkipForwardBackward) {
		context.datastore.edit { preferences ->
			preferences[skipForwardBackward] = duration.ordinal
		}
	}
	
	suspend fun setSkipTracksSmallerThan100KB(skip: Boolean) {
		context.datastore.edit { preferences ->
			preferences[skipTracksSmallerThan100KB] = skip
		}
	}
	
	suspend fun setSkipTracksShorterThan60Seconds(skip: Boolean) {
		context.datastore.edit { preferences ->
			preferences[skipTracksShorterThan60Seconds] = skip
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
	
	val getSortPlaylistOption: Flow<SortPlaylistOption> = context.datastore.data.map { preferences ->
		SortPlaylistOption.values()[preferences[sortPlaylistOption] ?: SortPlaylistOption.PLAYLIST_NAME.ordinal]
	}
	
	val getLastSongPlayed: Flow<Long> = context.datastore.data.map { preferences ->
		preferences[lastSongPlayed] ?: Song.default.audioID
	}
	
	val getPlaybackMode: Flow<PlaybackMode> = context.datastore.data.map { preferences ->
		PlaybackMode.values()[preferences[playbackMode] ?: PlaybackMode.REPEAT_OFF.ordinal]
	}
	
	val getSkipForwardBackward: Flow<SkipForwardBackward> = context.datastore.data.map { preferences ->
		SkipForwardBackward.values()[preferences[skipForwardBackward] ?: SkipForwardBackward.FIVE_SECOND.ordinal]
	}
	
	val isTracksSmallerThan100KBSkipped: Flow<Boolean> = context.datastore.data.map { preferences ->
		preferences[skipTracksSmallerThan100KB] ?: true
	}
	
	val isTracksShorterThan60SecondsSkipped: Flow<Boolean> = context.datastore.data.map { preferences ->
		preferences[skipTracksShorterThan60Seconds] ?: true
	}
	
	companion object {
		val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_datastore")
		
		val uiMode = intPreferencesKey(Preference.UI_MODE)
		val language = intPreferencesKey(Preference.LANGUAGE)
		val playbackMode = intPreferencesKey(Preference.PLAYBACK_MODE)
		val lastSongPlayed = longPreferencesKey(Preference.LAST_SONG_PLAYED)
		val sortSongOption = intPreferencesKey(Preference.SORT_SONG_OPTION)
		val sortAlbumOption = intPreferencesKey(Preference.SORT_ALBUM_OPTION)
		val sortArtistOption = intPreferencesKey(Preference.SORT_ARTIST_OPTION)
		val sortPlaylistOption = intPreferencesKey(Preference.SORT_PLAYLIST_OPTION)
		val skipForwardBackward = intPreferencesKey(Preference.SKIP_FORWARD_BACKWARD)
		val skipTracksSmallerThan100KB = booleanPreferencesKey(Preference.SKIP_TRACKS_SMALLER_THAN_100_KB)
		val skipTracksShorterThan60Seconds = booleanPreferencesKey(Preference.SKIP_TRACKS_SHORTER_THAN_60_SECONDS)
	}
}

val LocalAppDatastore = compositionLocalOf<AppDatastore?> { null }
