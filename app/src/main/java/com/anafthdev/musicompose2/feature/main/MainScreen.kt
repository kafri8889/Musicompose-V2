package com.anafthdev.musicompose2.feature.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.PlaylistOption
import com.anafthdev.musicompose2.data.SortType
import com.anafthdev.musicompose2.feature.album.album_list.AlbumListScreen
import com.anafthdev.musicompose2.feature.artist.artist_list.ArtistListScreen
import com.anafthdev.musicompose2.feature.home.HomeScreen
import com.anafthdev.musicompose2.feature.playlist.playlist_list.PlaylistListScreen
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isInDarkTheme
import com.anafthdev.musicompose2.foundation.extension.pagerTabIndicatorOffset
import com.anafthdev.musicompose2.foundation.theme.black01
import com.anafthdev.musicompose2.foundation.theme.black10
import com.anafthdev.musicompose2.foundation.theme.circle
import com.anafthdev.musicompose2.foundation.uicomponent.MoreOptionPopup
import com.anafthdev.musicompose2.foundation.uimode.data.LocalUiMode
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
	navController: NavController
) {
	
	val tabPages = listOf(
		stringResource(id = R.string.song),
		stringResource(id = R.string.album),
		stringResource(id = R.string.artist),
		stringResource(id = R.string.playlist)
	)
	
	val songController = LocalSongController.current
	
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState()
	
	var isMoreOptionPopupShowed by remember { mutableStateOf(false) }
	
	val scrollToPage: (Int) -> Unit = { page ->
		scope.launch { pagerState.animateScrollToPage(page) }
		Unit
	}
	
	BackHandler {
		when {
			pagerState.currentPage != 0 -> scrollToPage(0)
			else -> navController.popBackStack()
		}
	}
	
	Column(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		SmallTopAppBar(
			colors = TopAppBarDefaults.smallTopAppBarColors(
				containerColor = Color.Transparent
			),
			title = {},
			navigationIcon = {
				IconButton(
					onClick = {
						navController.navigate(MusicomposeDestination.Setting.route)
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
						navController.navigate(MusicomposeDestination.Search.route)
					}
				) {
					Icon(
						imageVector = Icons.Rounded.Search,
						contentDescription = null
					)
				}
				
				IconButton(
					onClick = {
						isMoreOptionPopupShowed = !isMoreOptionPopupShowed
					}
				) {
					Icon(
						imageVector = Icons.Rounded.MoreVert,
						contentDescription = null
					)
				}
				
				if (isMoreOptionPopupShowed) {
					MoreOptionPopup(
						options = listOf(
							stringResource(id = R.string.sort_by)
						),
						onDismissRequest = {
							isMoreOptionPopupShowed = false
						},
						onClick = { i ->
							when (i) {
								0 -> {
									scope.launch {
										songController?.hideBottomMusicPlayer()
										delay(800)
										navController.navigate(
											MusicomposeDestination.BottomSheet.Sort.createRoute(
												type = when (pagerState.currentPage) {
													0 -> SortType.SONG
													1 -> SortType.ALBUM
													2 -> SortType.ARTIST
													3 -> SortType.PLAYLIST
													else -> SortType.SONG
												}
											)
										)
									}
								}
							}
						},
						modifier = Modifier
							.padding(8.dp)
					)
				}
			}
		)
		
		TabRow(
			selectedTabIndex = pagerState.currentPage,
			indicator = { tabPositions ->
				Box(
					modifier = Modifier
						.pagerTabIndicatorOffset(
							pagerState = pagerState,
							tabPositions = tabPositions
						)
						.height(4.dp)
						.clip(circle)
						.background(MaterialTheme.colorScheme.primary)
				)
			}
		) {
			tabPages.forEachIndexed { i, title ->
				val selected = pagerState.currentPage == i
				
				Tab(
					selected = selected,
					text = {
						Text(
							text = title,
							style = LocalTextStyle.current.copy(
								color = if (selected) MaterialTheme.colorScheme.primary
								else {
									if (LocalUiMode.current.isInDarkTheme()) black10 else black01
								}
							)
						)
					},
					onClick = {
						scrollToPage(i)
					}
				)
			}
		}
		
		HorizontalPager(
			count = 4,
			state = pagerState
		) { page ->
			when (page) {
				0 -> HomeScreen()
				1 -> AlbumListScreen(navController = navController)
				2 -> ArtistListScreen(navController = navController)
				3 -> PlaylistListScreen(
					navController = navController,
					onNewPlaylist = {
						scope.launch {
							songController?.hideBottomMusicPlayer()
							delay(800)
							navController.navigate(
								MusicomposeDestination.BottomSheet.Playlist.createRoute(
									option = PlaylistOption.NEW
								)
							)
						}
					}
				)
			}
		}
	}
}
