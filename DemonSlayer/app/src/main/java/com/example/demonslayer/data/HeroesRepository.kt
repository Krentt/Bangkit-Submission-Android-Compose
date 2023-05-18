package com.example.demonslayer.data

import com.example.demonslayer.model.FakeHeroDataSource
import com.example.demonslayer.model.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HeroesRepository {

    private val heroes = mutableListOf<Hero>()

    init{
        if (heroes.isEmpty()) {
            FakeHeroDataSource.dummyHeroes.forEach{
                heroes.add(it)
            }
        }
    }

    fun getAllHeroes(): Flow<List<Hero>> {
        return flowOf(heroes)
    }

    companion object {
        @Volatile
        private var instance: HeroesRepository? = null

        fun getInstance(): HeroesRepository =
            instance ?: synchronized(this) {
                HeroesRepository().apply {
                    instance = this
                }
            }
    }
}