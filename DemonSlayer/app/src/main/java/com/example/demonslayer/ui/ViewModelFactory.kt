package com.example.demonslayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demonslayer.data.HeroesRepository
import com.example.demonslayer.ui.screen.detail.DetailHeroViewModel
import com.example.demonslayer.ui.screen.favorite.FavoriteViewModel
import com.example.demonslayer.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: HeroesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailHeroViewModel::class.java)){
            return DetailHeroViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}