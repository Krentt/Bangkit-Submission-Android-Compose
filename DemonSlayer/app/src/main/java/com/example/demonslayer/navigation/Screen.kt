package com.example.demonslayer.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
    object DetailHero: Screen("detail/{heroId}"){
        fun createRoute(heroId: Long) = "detail/$heroId"
    }
}