package com.example.demonslayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demonslayer.navigation.Navigationitem
import com.example.demonslayer.navigation.Screen
import com.example.demonslayer.ui.screen.detail.DetailScreen
import com.example.demonslayer.ui.screen.favorite.FavoriteScreen
import com.example.demonslayer.ui.screen.home.HomeScreen
import com.example.demonslayer.ui.screen.profile.ProfileScreen
import com.example.demonslayer.ui.theme.DemonSlayerTheme

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemonSlayerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailHero.route){
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding),
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { heroId ->
                        navController.navigate(Screen.DetailHero.createRoute(heroId))
                    })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailHero.route,
                arguments = listOf(navArgument("heroId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("heroId") ?: -1L
                DetailScreen(
                    heroId = id,
                    navigateBack = { navController.navigateUp() },
                    navigateToFavorite = {
                        navController.popBackStack()
                        navController.navigate(Screen.Favorite.route){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Favorite.route){
                FavoriteScreen(navigateToDetail = { heroId ->
                    navController.navigate(Screen.DetailHero.createRoute(heroId))
                })
            }

        }

    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            Navigationitem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
                desc = stringResource(id = R.string.home_page)
            ),
            Navigationitem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite,
                desc = stringResource(id = R.string.favorite_page)
            ),
            Navigationitem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
                desc = stringResource(id = R.string.about_page)
            ),
        )
        NavigationBar {
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.desc,
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    DemonSlayerTheme {
        DemonSlayerApp()
    }
}