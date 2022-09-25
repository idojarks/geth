package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.data.ArtworkToken
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.service.http.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Dragon721ArtworksScreen(
    artworkTokenList: List<ArtworkToken>,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = artworkTokenList,
            key = {
                it.index
            },
        ) { artwork ->
            ArtworkCard(artwork = artwork)
        }
    }

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    AnimatedVisibility(visible = showButton) {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            },
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "top",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtworkCard(
    artwork: ArtworkToken,
) {
    val model = Dragon721ViewModelProvider.current
    val scope = rememberCoroutineScope()

    val loadingTokenStateFlow = remember {
        MutableStateFlow<Pair<ArtworkToken.LoadingTokenState, String>>(Pair(ArtworkToken.LoadingTokenState.Start, ""))
    }
    val loadingTokenState by loadingTokenStateFlow.collectAsState()

    Card(
        onClick = {
            model.clickedArtworkIndex.value = artwork.index
        },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = Modifier.fillMaxWidth(),
    ) {
        when (loadingTokenState.first) {
            ArtworkToken.LoadingTokenState.Start -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Loading started",
                        )
                    }
                }

                scope.launch(Dispatchers.IO) {
                    loadingTokenStateFlow.emit(Pair(ArtworkToken.LoadingTokenState.Uri, ""))
                }
            }
            ArtworkToken.LoadingTokenState.Uri -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Waiting for token uri",
                        )
                    }
                }

                scope.launch(Dispatchers.IO) {
                    val tokenId = (artwork.index + 1).toLong()
                    val tokenUri = model.web3ContractService.tokenUri(tokenId)

                    loadingTokenStateFlow.emit(Pair(ArtworkToken.LoadingTokenState.ImageUri, tokenUri))
                }
            }
            ArtworkToken.LoadingTokenState.ImageUri -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Waiting for meta data",
                        )
                    }
                }

                scope.launch(Dispatchers.IO) {
                    val tokenUri = loadingTokenState.second
                    val imageUrl = HttpClient.getTokenImageUrl(tokenUri)

                    imageUrl?.let { url ->
                        loadingTokenStateFlow.emit(Pair(ArtworkToken.LoadingTokenState.Done, url))
                    }
                }
            }
            ArtworkToken.LoadingTokenState.Done -> {
                val imageUrl = loadingTokenState.second

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = artwork.context.title,
                    ) {
                        val painter = this.painter
                        val imageScope = this

                        Box(contentAlignment = Alignment.Center) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                is AsyncImagePainter.State.Error -> Text("Error")
                                is AsyncImagePainter.State.Success -> imageScope.SubcomposeAsyncImageContent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                )
                                is AsyncImagePainter.State.Empty -> Text("Empty")
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(
                                Brush.verticalGradient(
                                    0.75f to Color.Transparent, 1.0f to Color.Black,
                                ),
                            ),
                    )
                    Text(
                        text = artwork.context.title,
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .align(Alignment.BottomStart),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                    )
                }


                /*
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth(),
                ) {
                    val imageUrl = loadingTokenState.second

                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = artwork.context.title,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .padding(8.dp)
                    ) {
                        val painter = this.painter
                        val imageScope = this

                        Box(contentAlignment = Alignment.Center) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                is AsyncImagePainter.State.Error -> Text("Error")
                                is AsyncImagePainter.State.Success -> imageScope.SubcomposeAsyncImageContent(alignment = Alignment.Center)
                                is AsyncImagePainter.State.Empty -> Text("Empty")
                            }
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = artwork.context.title,
                            modifier = Modifier
                                .padding(4.dp),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Row {
                            Text(
                                text = artwork.context.artist,
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                */
            }
        }
    }
}