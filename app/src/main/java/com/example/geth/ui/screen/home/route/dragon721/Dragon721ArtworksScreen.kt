package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.data.ArtworkToken
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.service.http.HttpClient
import kotlinx.coroutines.Dispatchers
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
            count = artworkTokenList.size,
            //items = artworkTokenList,
            key = {
                artworkTokenList[it].index
                //it.index
            },
        ) { index ->
            ArtworkCard(artwork = artworkTokenList[index])
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

    val loadingState = artwork.loadingStateLiveData.observeAsState()

    if (loadingState.value == null) {
        return
    }

    val state = loadingState.value?.first
    val url = loadingState.value?.second

    Card(
        onClick = {
            model.clickedArtworkIndex.value = artwork.index
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        when (state) {
            ArtworkToken.LoadingState.Uri -> {
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

                    artwork.loadingStateLiveData.postValue(Pair(ArtworkToken.LoadingState.ImageUri, tokenUri))
                }
            }
            ArtworkToken.LoadingState.ImageUri -> {
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
                    url?.let {
                        val imageUrl = HttpClient.getTokenImageUrl(it)

                        imageUrl?.let { url ->
                            artwork.loadingStateLiveData.postValue(Pair(ArtworkToken.LoadingState.Done, url))
                        }
                    }
                }
            }
            ArtworkToken.LoadingState.Done -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    SubcomposeAsyncImage(
                        model = url,
                        contentDescription = artwork.context.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop,
                    ) {
                        val painter = this.painter
                        val imageScope = this

                        Box(contentAlignment = Alignment.Center) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                is AsyncImagePainter.State.Error -> Text("Error")
                                is AsyncImagePainter.State.Success -> imageScope.SubcomposeAsyncImageContent()
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
                                    0.75f to Color.Transparent,
                                    1.0f to Color.Black,
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
            }
        }
    }
}