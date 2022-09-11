package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.data.ArtworkToken
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.service.http.HttpClient
import com.example.geth.ui.screen.RootNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dragon721ArtworkDetailScreen(
    index: Int,
    title: String,
    artist: String,
) {
    val rootNavController = RootNavController.current
    val model = LocalEtherViewModelProvider.current

    var artwork: Contracts_Dragon721_sol_Dragon721.Artwork = Contracts_Dragon721_sol_Dragon721.Artwork(
        "title",
        "artist",
        "123",
        "456",
        "9090",
        BigInteger.valueOf(10),
        "description",
    )

    model.getArtwork(index)
        .onSuccess {
            it?.run {
                artwork = this
            }
        }
        .onFailure {
            InvalidArtwork(index)
            return
        }

    val ownerOfStateFlow = remember {
        MutableStateFlow("")
    }
    val ownerAddress by ownerOfStateFlow.collectAsState()

    LaunchedEffect(key1 = index) {
        withContext(Dispatchers.IO) {
            val tokenId = (index + 1).toLong()

            model.dragon721Service.ownerOf(tokenId)
                .onSuccess {
                    ownerOfStateFlow.emit(it)
                }
                .onFailure {
                    when (it) {
                        is IllegalStateException -> ownerOfStateFlow.emit("contract not loaded")
                        else -> ownerOfStateFlow.emit("request failed: ownerOf $tokenId")
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            rootNavController.navigate("home")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        val horizontalPadding = 8.dp
        val verticalPadding = 2.dp

        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            ArtworkImage(
                index = index,
                title = title,
            )
            Spacer(
                modifier = Modifier.padding(8.dp),
            )
            Column(
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = artwork.title,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding),
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(
                    modifier = Modifier.padding(8.dp),
                )
                Text(
                    text = "Artist",
                    modifier = Modifier.padding(horizontalPadding, verticalPadding),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = artwork.artist,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding),
                    style = MaterialTheme.typography.titleLarge,
                )
                if (artwork.artistAddress.isNotEmpty()) {
                    Text(
                        text = artwork.artistAddress,
                        modifier = Modifier.padding(horizontalPadding, verticalPadding),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(
                    modifier = Modifier.padding(8.dp),
                )
                Text(
                    text = "Owner",
                    modifier = Modifier.padding(horizontalPadding, verticalPadding),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = ownerAddress,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
private fun ArtworkImage(
    index: Int,
    title: String,
) {
    val loadingTokenStateFlow = remember {
        MutableStateFlow<Pair<ArtworkToken.LoadingTokenState, String>>(Pair(ArtworkToken.LoadingTokenState.Start, ""))
    }
    val loadingTokenState by loadingTokenStateFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val model = LocalEtherViewModelProvider.current

    val (loadingState, loadingUrl) = loadingTokenState

    when (loadingState) {
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
                val tokenId = (index + 1).toLong()
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
                val imageUrl = HttpClient.getTokenImageUrl(loadingUrl)

                imageUrl?.let { url ->
                    loadingTokenStateFlow.emit(Pair(ArtworkToken.LoadingTokenState.Done, url))
                }
            }
        }
        ArtworkToken.LoadingTokenState.Done -> Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = loadingUrl,
                contentDescription = title,
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
        }
    }
}

@Composable
private fun InvalidArtwork(index: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "warning",
        )
        Text(
            text = "Can't find artwork. index : $index",
        )
    }
}

@Preview
@Composable
private fun _Dragon721ArtworkDetailScreen() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        Dragon721ArtworkDetailScreen(
            index = 0,
            title = "beauty",
            artist = "john",
        )
    }
}