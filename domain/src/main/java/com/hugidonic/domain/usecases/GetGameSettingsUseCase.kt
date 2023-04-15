package com.hugidonic.domain.usecases

import com.hugidonic.domain.entity.GameSettings
import com.hugidonic.domain.entity.Level
import com.hugidonic.domain.repository.GameRepository

class GetGameSettingsUseCase(
	private val repository: GameRepository
) {
	operator fun invoke(level: Level): GameSettings {
		return repository.getGameSettings(level)
	}
}