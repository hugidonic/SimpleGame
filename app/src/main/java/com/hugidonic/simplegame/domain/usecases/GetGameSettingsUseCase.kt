package com.hugidonic.simplegame.domain.usecases

import com.hugidonic.simplegame.domain.entity.GameSettings
import com.hugidonic.simplegame.domain.entity.Level
import com.hugidonic.simplegame.domain.repository.GameRepository

class GetGameSettingsUseCase(
	private val repository: GameRepository
) {
	operator fun invoke(level: Level): GameSettings {
		return repository.getGameSettings(level)
	}
}