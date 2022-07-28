package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.data.EtherAccount
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.service.http.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dragon721TokensScreen(
    defaultAccount: EtherAccount,
) {
    val model = LocalEtherViewModelProvider.current
    val scope = rememberCoroutineScope()
    val tokenUrlList = model.tokenUrlList.observeAsState(emptyList())
    val artworks = model.artworks.observeAsState(emptyList())

    LaunchedEffect(key1 = defaultAccount) {
        if (defaultAccount.name.isBlank()) {
            return@LaunchedEffect
        }

        model.loadContract()

        launch(Dispatchers.IO) {
            val urls = mutableListOf<String>()

            model.artworks.value?.forEachIndexed { index, _ ->
                val tokenId = (index + 1).toLong()
                val tokenUri = model.dragon721Service.getTokenUri(tokenId)
                val imageUrl = HttpClient.getTokenImageUrl(tokenUri)
                urls.add(imageUrl
                    ?: "")
            }

            model.tokenUrlList.postValue(urls)
        }
    }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tokenUrlList.value.forEachIndexed { index, url ->
            item(
                key = index,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SubcomposeAsyncImage(
                            model = url,
                            contentDescription = artworks.value[index].title,
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
                                text = artworks.value[index].title,
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
                                    text = artworks.value[index].artist,
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

