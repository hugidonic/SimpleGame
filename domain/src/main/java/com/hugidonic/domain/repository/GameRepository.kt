package com.hugidonic.domain.repository

import com.hugidonic.domain.entity.GameSettings
import com.hugidonic.domain.entity.Level
import com.hugidonic.domain.entity.Question

interface GameRepository {

	fun generateQuestion(
		maxSumValue: Int,
		countOfOptions: Int,
	): Question

	fun getGameSettings(level: Level): GameSettings
}