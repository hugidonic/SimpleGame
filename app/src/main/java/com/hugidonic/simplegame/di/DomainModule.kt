package com.hugidonic.simplegame.di

import com.hugidonic.domain.repository.GameRepository
import com.hugidonic.domain.usecases.GenerateQuestionUseCase
import com.hugidonic.domain.usecases.GetGameSettingsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

	@Provides
	fun provideGenerateQuestionUseCase(gameRepository: GameRepository): GenerateQuestionUseCase {
		return GenerateQuestionUseCase(repository = gameRepository)
	}

	@Provides
	fun provideGetGameSettingsUseCase(gameRepository: GameRepository): GetGameSettingsUseCase {
		return GetGameSettingsUseCase(repository = gameRepository)
	}
}