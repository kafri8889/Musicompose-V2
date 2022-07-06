package com.anafthdev.musicompose2.data

sealed class MusicomposeDestination(open val route: String) {

	object Main: MusicomposeDestination("main")
	
	object Search: MusicomposeDestination("search")
	
	object Setting: MusicomposeDestination("setting")
	
	object Language: MusicomposeDestination("language")
	
	object Theme: MusicomposeDestination("theme")
	
	object Album: MusicomposeDestination("album/{albumID}") {
		fun createRoute(albumID: String): String {
			return "album/$albumID"
		}
	}
	
	object Artist: MusicomposeDestination("artist/{artistID}") {
		fun createRoute(artistID: String): String {
			return "artist/$artistID"
		}
	}
	
	object Playlist: MusicomposeDestination("playlist/{playlistID}") {
		fun createRoute(playlistID: Int): String {
			return "playlist/$playlistID"
		}
	}
	
	class BottomSheet {
		object Sort: MusicomposeDestination("bottom-sheet/sort/{type}") {
			fun createRoute(type: SortType): String {
				return "bottom-sheet/sort/${type.ordinal}"
			}
		}
		
		object Playlist: MusicomposeDestination("bottom-sheet/playlist/{option}/{playlistID}") {
			fun createRoute(
				option: PlaylistOption,
				playlistID: Int = com.anafthdev.musicompose2.data.model.Playlist.default.id
			): String {
				return "bottom-sheet/playlist/${option.ordinal}/$playlistID"
			}
		}
		
		object DeletePlaylist: MusicomposeDestination("delete-playlist/{playlistID}") {
			fun createRoute(playlistID: Int): String {
				return "delete-playlist/$playlistID"
			}
		}
	}

}
