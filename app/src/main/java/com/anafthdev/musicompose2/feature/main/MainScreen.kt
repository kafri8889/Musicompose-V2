package com.anafthdev.musicompose2.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.feature.album_list.AlbumScreen
import com.anafthdev.musicompose2.feature.artist_list.ArtistListScreen
import com.anafthdev.musicompose2.feature.home.HomeScreen
import com.anafthdev.musicompose2.foundation.uicomponent.MusicomposeTopAppBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
	
	Column(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		MusicomposeTopAppBar(
			navigationIcon = {
				IconButton(
					onClick = {
						// TODO: to SettingScreen
					}
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_setting),
						contentDescription = null
					)
				}
			},
			actions = {
				IconButton(
					onClick = {
						// TODO: to SearchScreen
					}
				) {
					Icon(
						imageVector = Icons.Rounded.Search,
						contentDescription = null
					)
				}
				
				IconButton(
					onClick = {
						// TODO: more option
					}
				) {
					Icon(
						imageVector = Icons.Rounded.MoreVert,
						contentDescription = null
					)
				}
			}
		)
		
		HorizontalPager(
			count = 4
		) { page ->
			when (page) {
				0 -> HomeScreen()
				1 -> AlbumScreen()
				2 -> ArtistListScreen()
				3 -> {
					// TODO: PlaylistScreen
				}
			}
		}
	}
}
