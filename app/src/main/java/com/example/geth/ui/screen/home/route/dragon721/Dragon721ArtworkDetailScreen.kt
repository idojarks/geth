package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import com.example.geth.ExceptionHandler
import com.example.geth.R
import com.example.geth.data.ArtworkToken
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.service.http.HttpClient
import com.example.geth.ui.screen.RootNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private val spacerPadding = 8.dp

@Composable
fun Dragon721ArtworkDetailScreen(
    index: Int,
) {
    val model = Dragon721ViewModelProvider.current

    if (index !in 0 until model.web3ContractService.artworks.size) {
        InvalidArtworkIndex(index = index)
        return
    }

    val artwork = model.web3ContractService.artworks[index]
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        ArtworkImage(
            index = index,
            artwork = artwork,
        )
        Spacer(
            modifier = Modifier.padding(8.dp),
        )
        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Title(
                title = artwork.title,
            )
            Artist(
                name = artwork.artist,
                address = artwork.artistAddress,
            )
            Owner(
                artworkIndex = index,
            )
            Price(
                price = artwork.price,
                exchangeTo = "KRW",
            )

            Text(
                text = artwork.description,
                style = MaterialTheme.typography.bodyLarge,
                softWrap = true,
            )
            Spacer(
                modifier = Modifier.padding(spacerPadding),
            )

            val date = Date(artwork.mintedDate.toLong())
            val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))

            Text(
                text = "Minted ${dateFormat.format(date)}",
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(
                modifier = Modifier.padding(spacerPadding),
            )




            Row(horizontalArrangement = Arrangement.End) {

            }
        }
    }
}

@Composable
private fun AvatarImage(
    artistName: String,
) {
    val loadingStateFlow = remember {
        MutableStateFlow("")
    }
    val loadingState by loadingStateFlow.collectAsState()

    when (loadingState) {
        "" -> {
            CircularProgressIndicator()

            val scope = rememberCoroutineScope()

            scope.launch(Dispatchers.IO) {
                HttpClient.getJson("https://api.github.com/users/$artistName")
                    ?.let {
                        val url = it.get("avatar_url")
                            .toString()
                        loadingStateFlow.emit(url)
                    }
                    ?: loadingStateFlow.emit("error")
            }
        }
        "error" -> Text(text = ExceptionHandler.lastExceptionMessage)
        else -> {
            SubcomposeAsyncImage(model = loadingState, contentDescription = "avatar image", alignment = Alignment.Center, modifier = Modifier.clip(RoundedCornerShape(percent = 50))) {
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
private fun ArtworkImage(
    index: Int,
    artwork: Artwork,
) {
    val loadingStateFlow = remember {
        MutableStateFlow<Pair<ArtworkToken.LoadingState, String>>(Pair(ArtworkToken.LoadingState.Start, ""))
    }
    val loadingTokenState by loadingStateFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val model = Dragon721ViewModelProvider.current

    val (loadingState, loadingUrl) = loadingTokenState

    when (loadingState) {
        ArtworkToken.LoadingState.Start -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
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
                loadingStateFlow.emit(Pair(ArtworkToken.LoadingState.Uri, ""))
            }
        }
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
                val tokenId = (index + 1).toLong()
                val tokenUri = model.web3ContractService.tokenUri(tokenId)

                loadingStateFlow.emit(Pair(ArtworkToken.LoadingState.ImageUri, tokenUri))
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
                val imageUrl = HttpClient.getTokenImageUrl(loadingUrl)

                imageUrl?.let { url ->
                    loadingStateFlow.emit(Pair(ArtworkToken.LoadingState.Done, url))
                }
            }
        }
        ArtworkToken.LoadingState.Done -> Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = loadingUrl,
                contentDescription = artwork.title,
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
private fun InvalidArtworkIndex(index: Int) {
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

@Composable
private fun Title(
    title: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.displaySmall,
    )
    Spacer(
        modifier = Modifier.padding(spacerPadding),
    )
}

@Composable
private fun Artist(
    name: String,
    address: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AvatarImage(artistName = name)
        Spacer(
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
            )
            if (address.isNotEmpty()) {
                Text(
                    text = address,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))

        var favorite by remember {
            mutableStateOf(false)
        }

        FilledTonalIconToggleButton(
            shape = CircleShape,
            checked = favorite,
            onCheckedChange = {
                favorite = it
            },
        ) {
            Icon(
                imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "favorite",
            )
        }
    }
    Spacer(
        modifier = Modifier.padding(spacerPadding),
    )
}

@Composable
private fun Owner(
    artworkIndex: Int,
) {
    val model = Dragon721ViewModelProvider.current

    val ownerOfStateFlow = remember {
        MutableStateFlow("")
    }
    val ownerAddress by ownerOfStateFlow.collectAsState()

    val scope = rememberCoroutineScope()

    scope.launch(Dispatchers.IO) {
        val tokenId = (artworkIndex + 1).toLong()
        ownerOfStateFlow.emit(model.web3ContractService.ownerOf(tokenId))
    }

    if (ownerAddress.isNotEmpty()) {
        Row {
            Text(
                text = "Owned by ",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = ownerAddress,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(
            modifier = Modifier.padding(spacerPadding),
        )
    }
}

@Composable
private fun Price(
    price: String,
    exchangeTo: String,
) {
    val exchangeFlowState = remember {
        MutableStateFlow("")
    }
    val exchange by exchangeFlowState.collectAsState()

    LaunchedEffect(key1 = price) {
        launch(Dispatchers.IO) {
            HttpClient.getJson("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=$exchangeTo")
                ?.let {
                    exchangeFlowState.emit(it.get(exchangeTo)
                        .toString())
                }
        }
    }

    Text(
        text = "Current price",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ethereum_eth_logo),
            contentDescription = "weth",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .width(12.dp),
        )
        Text(
            text = price,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .alignByBaseline()
                .padding(horizontal = 4.dp),
        )
        Text(
            text = "$exchangeTo $exchange",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .alignByBaseline()
                .padding(horizontal = 4.dp),
        )
    }
    Spacer(
        modifier = Modifier.padding(spacerPadding),
    )
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        Dragon721ViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        Dragon721ArtworkDetailScreen(
            index = 0,
        )
    }
}