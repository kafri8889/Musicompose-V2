package com.anafthdev.musicompose2.runtime.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
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
import com.anafthdev.musicompose2.data.SortType
import com.anafthdev.musicompose2.feature.language.LanguageScreen
import com.anafthdev.musicompose2.feature.main.MainScreen
import com.anafthdev.musicompose2.feature.playlist_sheet.PlaylistSheetScreen
import com.anafthdev.musicompose2.feature.search.SearchScreen
import com.anafthdev.musicompose2.feature.setting.SettingScreen
import com.anafthdev.musicompose2.feature.sort_sheet.SortSheetScreen
import com.anafthdev.musicompose2.feature.theme.ThemeScreen
import com.anafthdev.musicompose2.foundation.common.BottomSheetLayoutConfig
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MusicomposeNavHost(
	modifier: Modifier = Modifier
) {
	
	val songController = LocalSongController.current
	
	val bottomSheetNavigator = rememberBottomSheetNavigator()
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
					}
				)
			) { entry ->
				val playlistOption = PlaylistOption.values()[entry.arguments?.getInt("option") ?: 0]
				
				bottomSheetLayoutConfig = bottomSheetLayoutConfig.copy(
					sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
				)
				
				PlaylistSheetScreen(
					option = playlistOption,
					navController = navController
				)
			}
		}
	}
}
