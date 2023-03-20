package com.hugidonic.simplegame.domain.repository

import com.hugidonic.simplegame.domain.entity.GameSettings
import com.hugidonic.simplegame.domain.entity.Level
import com.hugidonic.simplegame.domain.entity.Question

interface GameRepository {

	fun generateQuestion(
		maxSumValue: Int,
		countOfOptions: Int,
	): Question

	fun getGameSettings(level: Level): GameSettings
}