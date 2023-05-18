package com.example.demonslayer.di

import com.example.demonslayer.data.HeroesRepository

object Injection {
    fun provideRepository(): HeroesRepository {
        return HeroesRepository.getInstance()
    }
}