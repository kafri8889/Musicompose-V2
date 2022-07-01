package com.anafthdev.musicompose2.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import com.anafthdev.musicompose2.data.model.Song

@Database(
	entities = [
		Song::class
	],
	version = 1,
	exportSchema = false
)
abstract class MusicomposeDatabase: RoomDatabase() {
	
	abstract fun songDao(): SongDao
	
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