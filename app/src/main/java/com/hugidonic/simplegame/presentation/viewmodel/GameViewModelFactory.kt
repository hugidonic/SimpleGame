package com.hugidonic.simplegame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hugidonic.domain.entity.Level
import com.hugidonic.domain.usecases.GenerateQuestionUseCase
import com.hugidonic.domain.usecases.GetGameSettingsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class GameViewModelFactory @AssistedInject constructor(
	private val generateQuestionUseCase: GenerateQuestionUseCase,
	private val getGameSettingsUseCase: GetGameSettingsUseCase,

	@Assisted("level") private val level: Level,
	@Assisted("progressAnswersString") private val progressAnswersString: String,
): ViewModelProvider.Factory {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
			return GameViewModel(
				level=level,
				generateQuestionUseCase=generateQuestionUseCase,
				getGameSettingsUseCase=getGameSettingsUseCase,
				progressAnswersString=progressAnswersString
			) as T
		} else {
			throw RuntimeException("Unknown view model: $modelClass")
		}
	}

	@AssistedFactory
	interface Factory {
		fun create(
			@Assisted("level") level: Level,
			@Assisted("progressAnswersString") progressAnswersString: String
		): GameViewModelFactory
	}
}