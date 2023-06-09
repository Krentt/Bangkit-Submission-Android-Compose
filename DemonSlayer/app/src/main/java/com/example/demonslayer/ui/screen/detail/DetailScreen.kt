package com.example.demonslayer.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.demonslayer.R
import com.example.demonslayer.di.Injection
import com.example.demonslayer.model.Hero
import com.example.demonslayer.ui.ViewModelFactory
import com.example.demonslayer.ui.common.UiState
import com.example.demonslayer.ui.components.FavoriteButton
import com.example.demonslayer.ui.components.LoadingScreen

@Composable
fun DetailScreen(
    heroId: Long,
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit,
    viewModel: DetailHeroViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
                viewModel.getHeroById(heroId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    hero = data,
                    onBackClick = navigateBack,
                    onAddToFavorite = {
                        viewModel.updateFavorite(data)
                        navigateToFavorite()
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    hero: Hero,
    onBackClick: () -> Unit,
    onAddToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Row(modifier = modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier
                            .clickable { onBackClick() })
                    Text(
                        text = "Detail Hero",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = hero.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = hero.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(8.dp)
                )
                Row(modifier = modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.height, hero.height),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.gender, hero.gender),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.weight, hero.weight),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(modifier = modifier.padding(16.dp)) {
            if (hero.favorite){
                FavoriteButton(
                    text = stringResource(id = R.string.remove_favorite),
                    enabled = true,
                    onClick = {
                        onAddToFavorite()
                    }
                )
            } else {
                FavoriteButton(
                    text = stringResource(id = R.string.add_favorite),
                    enabled = true,
                    onClick = {
                        onAddToFavorite()
                    }
                )
            }
        }

    }
}
