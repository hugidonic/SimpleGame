package com.hugidonic.simplegame.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hugidonic.domain.entity.Level

class GameViewModelFactory(
	private val application: Application,
	private val level: com.hugidonic.domain.entity.Level
): ViewModelProvider.Factory {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
			return GameViewModel(application, level) as T
		} else {
			throw RuntimeException("Unknown view model: $modelClass")
		}

	}
}