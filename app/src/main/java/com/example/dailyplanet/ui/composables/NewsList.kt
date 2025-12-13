package com.example.dailyplanet.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailyplanet.models.Article
import com.example.dailyplanet.repository.AppFont
import com.example.dailyplanet.repository.TextSize
import com.example.dailyplanet.repository.ViewType
import com.example.dailyplanet.ui.viewmodel.NewsPagingState
import com.example.dailyplanet.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun NewsList(
    state: NewsPagingState,
    savedArticles: List<Article>,
    viewModel: NewsViewModel,
    onLoadMore: () -> Unit,
    textSize: TextSize,
    font: AppFont,
    viewType: ViewType
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        val gridState = rememberLazyGridState()

        // THIS IS THE EDIT: The spinner is replaced with the shimmer effect.
        if (state.isLoading && state.articles.isEmpty()) {
            ShimmerList()
        } else if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        } else if (state.articles.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No articles found.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            if (viewType == ViewType.Tile) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    state = gridState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = state.articles,
                        key = { index, article -> "${article.url}-$index" }
                    ) { _, article ->
                        val isFavorite = savedArticles.any { it.url == article.url }
                        NewsCard(
                            article = article,
                            viewModel = viewModel,
                            isFavorite = isFavorite,
                            textSize = textSize,
                            font = font,
                            viewType = viewType
                        )
                    }
                    if (state.isLoading && state.articles.isNotEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = state.articles,
                        key = { index, article -> "${article.url}-$index" }
                    ) { _, article ->
                        val isFavorite = savedArticles.any { it.url == article.url }
                        NewsCard(
                            article = article,
                            viewModel = viewModel,
                            isFavorite = isFavorite,
                            textSize = textSize,
                            font = font,
                            viewType = viewType
                        )
                    }
                    if (state.isLoading && state.articles.isNotEmpty()) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(listState, gridState, viewType) {
            if (viewType == ViewType.Tile) {
                snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .distinctUntilChanged()
                    .filter { lastVisibleItemIndex ->
                        lastVisibleItemIndex != null &&
                                lastVisibleItemIndex >= state.articles.size - 2 &&
                                !state.isLoading &&
                                !state.endReached
                    }
                    .collect {
                        onLoadMore()
                    }
            } else {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .distinctUntilChanged()
                    .filter { lastVisibleItemIndex ->
                        lastVisibleItemIndex != null &&
                                lastVisibleItemIndex >= state.articles.size - 2 &&
                                !state.isLoading &&
                                !state.endReached
                    }
                    .collect {
                        onLoadMore()
                    }
            }
        }
    }
}