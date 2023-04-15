package com.hugidonic.simplegame.di

import dagger.Module

@Module(includes = [DomainModule::class, DataModule::class])
class AppModule {

}