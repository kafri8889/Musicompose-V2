package com.anafthdev.musicompose2.feature.musicompose

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.musicompose.environment.MusicomposeEnvironment
import com.anafthdev.musicompose2.foundation.service.MediaPlayerService
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MusicomposeViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	environment: MusicomposeEnvironment
): StatefulViewModel<MusicomposeState, Unit, MusicomposeAction, MusicomposeEnvironment>(
	MusicomposeState(),
	environment
) {
	
	private val serviceIntent = Intent(context, MediaPlayerService::class.java).apply {
		putExtra("musicomposeState", state.value)
	}
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			state.collect { musicomposeState ->
				serviceIntent.putExtra("musicomposeState", musicomposeState)
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					context.startForegroundService(serviceIntent)
				} else context.startService(serviceIntent)
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSongs().collect { songs ->
				setState {
					copy(
						songs = songs
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getCurrentSongQueue().collect { songs ->
				setState {
					copy(
						currentSongQueue = songs
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getCurrentPlayedSong().collect { song ->
				setState {
					copy(
						currentSongPlayed = song
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getCurrentDuration().collect { duration ->
				setState {
					copy(
						currentDuration = duration
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getPlaybackMode().collect { mode ->
				setState {
					copy(
						playbackMode = mode
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSkipForwardBackward().collect { skipForwardBackward ->
				setState {
					copy(
						skipForwardBackward = skipForwardBackward
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isPlaying().collect { isPlaying ->
				setState {
					copy(
						isPlaying = isPlaying
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isShuffled().collect { isShuffled ->
				setState {
					copy(
						isShuffled = isShuffled
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.isBottomMusicPlayerShowed().collect { isShowed ->
				setState {
					copy(
						isBottomMusicPlayerShowed = isShowed
					)
				}
			}
		}
	}
	
	override fun dispatch(action: MusicomposeAction) {
		when (action) {
			is MusicomposeAction.Play -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.play(action.song)
				}
			}
			is MusicomposeAction.SnapTo -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.snapTo(action.duration)
				}
			}
			is MusicomposeAction.PlayAll -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.playAll(action.songs)
				}
			}
			is MusicomposeAction.SetPlaying -> {
				viewModelScope.launch(environment.dispatcher) {
					if (action.isPlaying) environment.resume()
					else environment.pause()
				}
			}
			is MusicomposeAction.SetShuffle -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setShuffle(action.isShuffled)
				}
			}
			is MusicomposeAction.UpdateSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.updateSong(action.song)
				}
			}
			is MusicomposeAction.UpdateQueueSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.updateQueueSong(action.songs)
				}
			}
			is MusicomposeAction.SetShowBottomMusicPlayer -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setShowBottomMusicPlayer(action.isShowed)
				}
			}
			is MusicomposeAction.PlayLastSongPlayed -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.playLastSongPlayed()
				}
			}
			is MusicomposeAction.ChangePlaybackMode -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.changePlaybackMode()
				}
			}
			is MusicomposeAction.Backward -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.backward()
				}
			}
			is MusicomposeAction.Stop -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.stop()
				}
			}
			is MusicomposeAction.Previous -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.previous()
				}
			}
			is MusicomposeAction.Forward -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.forward()
				}
			}
			is MusicomposeAction.Next -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.next()
				}
			}
		}
	}
	
}