package com.anafthdev.musicompose2.foundation.localized

import com.anafthdev.musicompose2.data.preference.Language

sealed class LocalizedAction {
	data class SetLanguage(val lang: Language): LocalizedAction()
}