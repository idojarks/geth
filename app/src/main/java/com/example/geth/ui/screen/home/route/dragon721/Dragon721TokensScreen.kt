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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.data.ArtworkToken
import com.example.geth.data.EtherAccount
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.service.http.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
private fun getArtworkTokenList(
    artworkToken: ArtworkToken,
): State<SnapshotStateList<ArtworkToken>> {
    val list = remember {
        mutableStateListOf<ArtworkToken>()
    }

    return produceState(initialValue = list, artworkToken) {
        if (artworkToken.imageUrl.isNotEmpty()) {
            list.add(artworkToken)
        }
        value = list
    }
}

sealed class LoadingState {
    interface Message {
        val loadingMessage: String
    }

    object Contract : Message {
        override val loadingMessage: String = "Loading contract"
    }

    object Artworks : Message {
        override val loadingMessage: String = "Loading artworks"
    }

    object Done : Message {
        override val loadingMessage: String = ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dragon721TokensScreen(
    defaultAccount: EtherAccount?,
) {
    val model = LocalEtherViewModelProvider.current
    val scope = rememberCoroutineScope()

    val loadingStateFlow = remember {
        MutableStateFlow<LoadingState.Message>(LoadingState.Contract)
    }
    val loadingState by loadingStateFlow.collectAsState()

    val artworkTokenListStateFlow = remember {
        MutableStateFlow(listOf<ArtworkToken>())
    }
    val artworkTokenList by artworkTokenListStateFlow.collectAsState()

    LaunchedEffect(key1 = defaultAccount) {
        defaultAccount?.let {
            loadingStateFlow.emit(LoadingState.Contract)
            model.loadContract(it)

            loadingStateFlow.emit(LoadingState.Artworks)
            model.loadArtworks()
                .run {
                    loadingStateFlow.emit(LoadingState.Done)

                    val list = mutableListOf<ArtworkToken>()

                    forEachIndexed { index, artwork ->
                        launch(Dispatchers.IO) {
                            val token = ArtworkToken()
                            token.index = index
                            token.context = artwork

                            list.add(token)
                            list.sortBy { elem ->
                                elem.index
                            }

                            artworkTokenListStateFlow.emit(list)
                        }
                    }
                }
        }
    }

    if (loadingState == LoadingState.Contract || loadingState == LoadingState.Artworks) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
                Text(text = loadingState.loadingMessage)
            }
        }

        return
    }

    val listState = rememberLazyListState()

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
    val scope = rememberCoroutineScope()

    val loadingTokenStateFlow = remember {
        MutableStateFlow<Pair<ArtworkToken.LoadingTokenState, String>>(Pair(ArtworkToken.LoadingTokenState.Uri, ""))
    }
    val loadingTokenState by loadingTokenStateFlow.collectAsState()

    Card(modifier = Modifier.fillMaxWidth()) {
        when (loadingTokenState.first) {
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

@Preview
@Composable
private fun PreviewTokens() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
    ) {
        Dragon721TokensScreen(
            defaultAccount = EtherAccount(
                name = "john",
                address = "0x00",
                privateKey = "0x00",
                isDefault = true,
            ),
        )
    }
}

