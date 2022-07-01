package com.anafthdev.musicompose2.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Song

object SongUtil {
	
	fun getSong(
		context: Context,
	): List<Song> {
		
		val songList = ArrayList<Song>()
		
		val audioUriExternal = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
		
		val songProjection = listOf(
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.DURATION,
			MediaStore.Audio.Media.ALBUM_ID,
			MediaStore.Audio.Media.DATE_ADDED,
		)
		
		val cursorIndexSongID: Int
		val cursorIndexSongDisplayName: Int
		val cursorIndexSongTitle: Int
		val cursorIndexSongArtist: Int
		val cursorIndexSongAlbum: Int
		val cursorIndexSongDuration: Int
		val cursorIndexSongAlbumID: Int
		val cursorIndexSongDateAdded: Int
		
		val songCursor = context.contentResolver.query(
			audioUriExternal,
			songProjection.toTypedArray(),
			null,
			null,
			null
		)
		
		if (songCursor != null) {
			cursorIndexSongID = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
			cursorIndexSongDisplayName = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
			cursorIndexSongTitle = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
			cursorIndexSongArtist = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
			cursorIndexSongAlbum = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
			cursorIndexSongDuration = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
			cursorIndexSongAlbumID = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
			cursorIndexSongDateAdded = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
			
			while (songCursor.moveToNext()) {
				val audioID = songCursor.getLong(cursorIndexSongID)
				val displayName = songCursor.getString(cursorIndexSongDisplayName)
				val title = songCursor.getString(cursorIndexSongTitle)
				val artist = songCursor.getString(cursorIndexSongArtist)
				val album = songCursor.getString(cursorIndexSongAlbum)
				val duration = songCursor.getLong(cursorIndexSongDuration)
				val albumId = songCursor.getString(cursorIndexSongAlbumID)
				val dateAdded = songCursor.getLong(cursorIndexSongDateAdded)
				
				val albumPath = Uri.withAppendedPath(Uri.parse("content://media/external/audio/albumart"), albumId)
				val path = Uri.withAppendedPath(audioUriExternal, "" + audioID)
				
				songList.add(
					Song(
						audioID = audioID,
						displayName = displayName,
						title = title,
						artist = if (artist.equals("<unknown>", true)) context.getString(R.string.unknown) else artist,
						album = album,
						albumID = albumId,
						duration = duration,
						albumPath = albumPath.toString(),
						path = path.toString(),
						dateAdded = dateAdded
					)
				)
			}
			
			songCursor.close()
		}
		
		return songList
	}
	
}