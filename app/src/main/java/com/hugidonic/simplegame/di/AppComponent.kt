package com.hugidonic.simplegame.di

import com.hugidonic.simplegame.presentation.GameFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
	fun inject(gameFragment: GameFragment)
}