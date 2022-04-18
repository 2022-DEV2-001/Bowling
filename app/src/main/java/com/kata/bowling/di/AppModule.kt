package com.kata.bowling.di

import com.kata.bowling.BowlingGame
import com.kata.bowling.repository.BowlingRepository
import com.kata.bowling.repository.BowlingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBowlingClass() = BowlingGame()

    @Singleton
    @Provides
    fun provideBowlingRepository(bowlingGame: BowlingGame): BowlingRepository =
        BowlingRepositoryImpl(bowlingGame)
}
