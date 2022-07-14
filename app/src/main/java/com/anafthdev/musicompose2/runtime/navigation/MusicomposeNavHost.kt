package com.anafthdev.musicompose2.runtime.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.PlaylistOption
import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.SortType
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.feature.album.album.AlbumScreen
import com.anafthdev.musicompose2.feature.artist.artist.ArtistScreen
import com.anafthdev.musicompose2.feature.language.LanguageScreen
import com.anafthdev.musicompose2.feature.main.MainScreen
import com.anafthdev.musicompose2.feature.music_player_sheet.MusicPlayerSheetScreen
import com.anafthdev.musicompose2.feature.playlist.delete_playlist.DeletePlaylistScreen
import com.anafthdev.musicompose2.feature.playlist.playlist.PlaylistScreen
import com.anafthdev.musicompose2.feature.playlist.playlist_sheet.PlaylistSheetScreen
import com.anafthdev.musicompose2.feature.scan_options.ScanOptionsScreen
import com.anafthdev.musicompose2.feature.search.SearchScreen
import com.anafthdev.musicompose2.feature.setting.SettingScreen
import com.anafthdev.musicompose2.feature.song_selector.SongSelectorScreen
import com.anafthdev.musicompose2.feature.sort_sheet.SortSheetScreen
import com.anafthdev.musicompose2.feature.theme.ThemeScreen
import com.anafthdev.musicompose2.foundation.common.BottomSheetLayoutConfig
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MusicomposeNavHost(
	modifier: Modifier = Modifier
) {
	
	val songController = LocalSongController.current
	
	val sheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		animationSpec = SwipeableDefaults.AnimationSpec,
		skipHalfExpanded = true
	)
	
	val bottomSheetNavigator = remember(sheetState) {
		BottomSheetNavigator(sheetState = sheetState)
	}
	
	val navController = rememberNavController(bottomSheetNavigator)
	
	var bottomSheetLayoutConfig by remember { mutableStateOf(BottomSheetLayoutConfig()) }
	
	LaunchedEffect(bottomSheetNavigator.navigatorSheetState.isVisible) {
		if (!bottomSheetNavigator.navigatorSheetState.isVisible) songController?.showBottomMusicPlayer()
	}
	
	ModalBottomSheetLayout(
		sheetBackgroundColor = bottomSheetLayoutConfig.sheetBackgroundColor,
		bottomSheetNavigator = bottomSheetNavigator,
		sheetShape = MaterialTheme.shapes.large.copy(
			bottomEnd = CornerSize(0),
			bottomStart = CornerSize(0)
		),
		modifier = modifier
	) {
		NavHost(
			navController = navController,
			startDestination = MusicomposeDestination.Main.route,
			modifier = Modifier
				.fillMaxSize()
		) {
			composable(MusicomposeDestination.Main.route) {
				MainScreen(navController = navController)
			}
			
			composable(MusicomposeDestination.Search.route) {
				SearchScreen(navController = navController)
			}
			
			composable(MusicomposeDestination.Setting.route) {
				SettingScreen(navController = navController)
			}
			
			composable(MusicomposeDestination.Language.route) {
				LanguageScreen(navController = navController)
			}
			
			composable(MusicomposeDestination.Theme.route) {
				ThemeScreen(navController = navController)
			}
			
			composable(MusicomposeDestination.ScanOptions.route) {
				ScanOptionsScreen(navController = navController)
			}
			
			composable(
				route = MusicomposeDestination.Album.route,
				arguments = listOf(
					navArgument(
						name = "albumID"
					) {
						type = NavType.StringType
					}
				)
			) { entry ->
				val albumID = entry.arguments?.getString("albumID") ?: Album.default.id
				
				AlbumScreen(
					albumID = albumID,
					navController = navController
				)
			}
			
			composable(
				route = MusicomposeDestination.Artist.route,
				arguments = listOf(
					navArgument(
						name = "artistID"
					) {
						type = NavType.StringType
					}
				)
			) { entry ->
				val artistID = entry.arguments?.getString("artistID") ?: Artist.default.id
				
				ArtistScreen(
					artistID = artistID,
					navController = navController
				)
			}
			
			composable(
				route = MusicomposeDestination.Playlist.route,
				arguments = listOf(
					navArgument(
						name = "playlistID"
					) {
						type = NavType.IntType
					}
				)
			) { entry ->
				val playlistID = entry.arguments?.getInt("playlistID") ?: Playlist.default.id
				
				PlaylistScreen(
					playlistID = playlistID,
					navController = navController
				)
			}
			
			composable(
				route = MusicomposeDestination.SongSelector.route,
				arguments = listOf(
					navArgument(
						name = "type"
					) {
						type = NavType.IntType
					},
					navArgument(
						name = "playlistID"
					) {
						type = NavType.IntType
					}
				)
			) { entry ->
				val type = SongSelectorType.values()[entry.arguments?.getInt("type") ?: SongSelectorType.ADD_SONG.ordinal]
				val playlistID = entry.arguments?.getInt("playlistID") ?: Playlist.default.id
				
				SongSelectorScreen(
					type = type,
					playlistID = playlistID,
					navController = navController
				)
			}
			
			bottomSheet(MusicomposeDestination.BottomSheet.MusicPlayer.route) {
				
				bottomSheetLayoutConfig = bottomSheetLayoutConfig.copy(
					sheetBackgroundColor = MaterialTheme.colorScheme.background
				)
				
				MusicPlayerSheetScreen(
					navController = navController,
					bottomSheetLayoutConfig = bottomSheetLayoutConfig
				)
			}
			
			bottomSheet(
				route = MusicomposeDestination.BottomSheet.Sort.route,
				arguments = listOf(
					navArgument(
						name = "type"
					) {
						type = NavType.IntType
					}
				)
			) { entry ->
				val sortType = SortType.values()[entry.arguments?.getInt("type") ?: 0]
				
				bottomSheetLayoutConfig = bottomSheetLayoutConfig.copy(
					sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
				)
				
				SortSheetScreen(
					sortType = sortType,
					navController = navController
				)
			}
			
			bottomSheet(
				route = MusicomposeDestination.BottomSheet.Playlist.route,
				arguments = listOf(
					navArgument(
						name = "option"
					) {
						type = NavType.IntType
					},
					navArgument(
						name = "playlistID"
					) {
						type = NavType.IntType
					}
				)
			) { entry ->
				val playlistOption = PlaylistOption.values()[entry.arguments?.getInt("option") ?: 0]
				val playlistID = entry.arguments?.getInt("playlistID") ?: Playlist.default.id
				
				bottomSheetLayoutConfig = bottomSheetLayoutConfig.copy(
					sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
				)
				
				PlaylistSheetScreen(
					playlistID = playlistID,
					option = playlistOption,
					navController = navController
				)
			}
			
			bottomSheet(
				route = MusicomposeDestination.BottomSheet.DeletePlaylist.route,
				arguments = listOf(
					navArgument(
						name = "playlistID"
					) {
						type = NavType.IntType
					}
				)
			) { entry ->
				val playlistID = entry.arguments?.getInt("playlistID") ?: Playlist.default.id
				
				bottomSheetLayoutConfig = bottomSheetLayoutConfig.copy(
					sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
				)
				
				DeletePlaylistScreen(
					playlistID = playlistID,
					navController = navController
				)
			}
		}
	}
}
