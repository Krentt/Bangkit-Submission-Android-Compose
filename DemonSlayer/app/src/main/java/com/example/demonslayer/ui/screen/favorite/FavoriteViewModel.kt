package com.example.demonslayer.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demonslayer.data.HeroesRepository
import com.example.demonslayer.model.Hero
import com.example.demonslayer.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: HeroesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Hero>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Hero>>>
        get() = _uiState

    fun getFavoriteHero() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFavoriteHero()
                .collect { heroes ->
                    _uiState.value = UiState.Success(heroes)
                }
        }
    }
}