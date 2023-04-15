package com.hugidonic.simplegame.di

import com.hugidonic.data.GameRepositoryImpl
import com.hugidonic.domain.repository.GameRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
	@Provides
	fun provideGameRepository(): GameRepository {
		return GameRepositoryImpl()
	}
}