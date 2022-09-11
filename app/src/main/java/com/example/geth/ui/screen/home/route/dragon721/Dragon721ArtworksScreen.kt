package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.data.ArtworkToken
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.service.http.HttpClient
import com.example.geth.ui.screen.RootNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Dragon721ArtworksScreen(artworkTokenList: List<ArtworkToken>) {
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
    val model = LocalEtherViewModelProvider.current
    val rootNavController = RootNavController.current
    val scope = rememberCoroutineScope()

    val loadingTokenStateFlow = remember {
        MutableStateFlow<Pair<ArtworkToken.LoadingTokenState, String>>(Pair(ArtworkToken.LoadingTokenState.Start, ""))
    }
    val loadingTokenState by loadingTokenStateFlow.collectAsState()

    OutlinedCard(
        onClick = {
            rootNavController.navigate(
                route = "artworkDetail?id=${artwork.index}&title=${artwork.context.title}&artist=${artwork.context.artist}",
            )
        },
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

                scope.launch {
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

                scope.launch {
                    val tokenId = (artwork.index + 1).toLong()
                    val tokenUri = model.dragon721Service.getTokenUri(tokenId)
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
            ArtworkToken.LoadingTokenState.Done -> Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val imageUrl = loadingTokenState.second

                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = artwork.context.title,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .padding(8.dp),
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                        is AsyncImagePainter.State.Error -> Text("Error")
                        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                        is AsyncImagePainter.State.Empty -> Text("Empty")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                ) {
                    Text(
                        text = artwork.context.title,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        fontSize = 40.sp,
                        //fontWeight = FontWeight.Bold,
                    )
                    Row {
                        Text(
                            text = "by",
                            modifier = Modifier.padding(4.dp),
                            fontSize = 14.sp,
                        )
                        Text(
                            text = artwork.context.artist,
                            modifier = Modifier.padding(4.dp),
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }
    }
}