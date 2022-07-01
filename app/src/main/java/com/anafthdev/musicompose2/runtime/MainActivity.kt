package com.anafthdev.musicompose2.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.feature.musicompose.Musicompose
import com.anafthdev.musicompose2.feature.theme.Musicompose2
import com.anafthdev.musicompose2.foundation.localized.LocalizedActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: LocalizedActivity() {
	
	@Inject lateinit var appDatastore: AppDatastore
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Musicompose(appDatastore)
		}
	}
}
