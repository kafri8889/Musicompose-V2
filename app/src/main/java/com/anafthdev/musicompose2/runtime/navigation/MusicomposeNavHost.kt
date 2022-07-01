package com.anafthdev.musicompose2.runtime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.feature.home.HomeScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MusicomposeNavHost() {
	
	val bottomSheetNavigator = rememberBottomSheetNavigator()
	val navController = rememberNavController(bottomSheetNavigator)
	
	ModalBottomSheetLayout(
		bottomSheetNavigator = bottomSheetNavigator
	) {
		NavHost(
			navController = navController,
			startDestination = MusicomposeDestination.Home.route
		) {
			composable(MusicomposeDestination.Home.route) {
				HomeScreen()
			}
		}
	}
}
