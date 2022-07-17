package ru.gozerov.whackamoleapp.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.whackamoleapp.engine.GameEngine
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class EngineModule {

    @Singleton
    @Provides
    fun provideGameEngine() = GameEngine()

}