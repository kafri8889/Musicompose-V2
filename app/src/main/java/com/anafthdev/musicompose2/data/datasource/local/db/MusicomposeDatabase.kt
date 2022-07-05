package com.anafthdev.musicompose2.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anafthdev.musicompose2.data.datasource.local.db.playlist.PlaylistDao
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song

@Database(
	entities = [
		Song::class,
		Playlist::class
	],
	version = 1,
	exportSchema = false
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class MusicomposeDatabase: RoomDatabase() {
	
	abstract fun songDao(): SongDao
	
	abstract fun playlistDao(): PlaylistDao
	
	companion object {
		private var INSTANCE: MusicomposeDatabase? = null
		
		fun getInstance(context: Context): MusicomposeDatabase {
			if (INSTANCE == null) {
				synchronized(MusicomposeDatabase::class) {
					INSTANCE = Room.databaseBuilder(
						context,
						MusicomposeDatabase::class.java,
						"musicompose.db"
					).build()
				}
			}
			
			return INSTANCE!!
		}
	}
}