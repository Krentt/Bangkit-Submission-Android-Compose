package com.example.demonslayer.data

import com.example.demonslayer.model.FakeHeroDataSource
import com.example.demonslayer.model.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class HeroesRepository {

    private val heroes = mutableListOf<Hero>()

    init {
        if (heroes.isEmpty()) {
            FakeHeroDataSource.dummyHeroes.forEach {
                heroes.add(it)
            }
        }
    }

    fun getAllHeroes(): Flow<List<Hero>> {
        return flowOf(heroes)
    }

    fun getHeroById(heroId: Long): Hero {
        return heroes.first {
            it.id == heroId
        }
    }

    fun getFavoriteHero(): Flow<List<Hero>> {
        return getAllHeroes()
            .map { heroes ->
                heroes.filter { hero ->
                    hero.favorite
                }
            }
    }

    fun searchHeroes(query: String): List<Hero> {
        return heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun updateFavorite(heroId: Long): Flow<Boolean> {
        val index = heroes.indexOfFirst { it.id == heroId }
        val result = if (index >= 0) {
            val hero = heroes[index]
            heroes[index] = hero.copy(
                id = hero.id,
                image = hero.image,
                name = hero.name,
                height = hero.height,
                weight = hero.weight,
                gender = hero.gender,
                favorite = !hero.favorite
            )
            true
        } else {
            false
        }
        return flowOf(result)
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