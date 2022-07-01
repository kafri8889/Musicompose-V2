package com.anafthdev.musicompose2.feature.musicompose.environment

import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class MusicomposeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IMusicomposeEnvironment {
	
	override fun getSongs(): Flow<List<Song>> {
		return repository.getSongs()
	}
	
}