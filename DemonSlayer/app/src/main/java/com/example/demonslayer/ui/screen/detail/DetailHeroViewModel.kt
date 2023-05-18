package com.example.demonslayer.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demonslayer.data.HeroesRepository
import com.example.demonslayer.model.Hero
import com.example.demonslayer.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailHeroViewModel(
    private val repository: HeroesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Hero>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Hero>>
        get() = _uiState

    fun getHeroById(heroId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(
                repository.getHeroById(
                    heroId = heroId
                )
            )
        }
    }
}