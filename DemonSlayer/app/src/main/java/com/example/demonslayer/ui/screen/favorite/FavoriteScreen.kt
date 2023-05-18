package com.example.demonslayer.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demonslayer.R
import com.example.demonslayer.di.Injection
import com.example.demonslayer.model.Hero
import com.example.demonslayer.ui.ViewModelFactory
import com.example.demonslayer.ui.common.UiState
import com.example.demonslayer.ui.components.HeroListItem
import com.example.demonslayer.ui.components.LoadingScreen
import com.example.demonslayer.ui.screen.home.HomeContent
import com.example.demonslayer.ui.theme.DemonSlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
                viewModel.getFavoriteHero()
            }

            is UiState.Success -> {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                stringResource(id = R.string.my_favorite),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
                    )
                }) { innerPadding ->
                    if (uiState.data.isNotEmpty()) {
                        FavoriteContent(
                            heroes = uiState.data,
                            modifier = modifier.padding(innerPadding),
                            navigateToDetail = navigateToDetail
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxSize()
                        ){
                            Text(
                                text = stringResource(id = R.string.empty),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = modifier
    ) {
        items(heroes) { data ->
            HeroListItem(
                name = data.name,
                photoUrl = data.image,
                modifier = Modifier.clickable(onClick = { navigateToDetail(data.id) })
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    DemonSlayerTheme {
        FavoriteScreen(navigateToDetail = {})
    }
}