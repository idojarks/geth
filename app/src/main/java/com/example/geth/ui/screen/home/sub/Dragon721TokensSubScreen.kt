package com.example.geth.ui.screen.home.sub

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherUrl
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.Dragon721Service
import com.example.geth.service.blockchain.InspectionModeDragon721Service
import com.example.geth.service.http.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTextApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Dragon721TokensSubScreen(
    defaultAccount: EtherAccount,
    symbol: String,
    artworks: List<Contracts_Dragon721_sol_Dragon721.Artwork>,
    onLoadContract: () -> Unit,
) {
    val model = LocalEtherViewModelProvider.current
    val scope = rememberCoroutineScope()
    val tokenUrlList = model.tokenUrlList.observeAsState()

    LaunchedEffect(key1 = defaultAccount) {
        if (defaultAccount.name.isBlank()) {
            return@LaunchedEffect
        }

        model.dragon721Service.loadContract(
            contractAddress = EtherUrl.contractAddress,
            privateKey = defaultAccount.privateKey,
        )

        onLoadContract()
    }

    scope.launch(Dispatchers.IO) {
        val urls = mutableListOf<String>()

        artworks.forEachIndexed { index, _ ->
            val tokenId = (index + 1).toLong()
            val tokenUri = model.dragon721Service.getTokenUri(tokenId)
            val imageUrl = HttpClient.getTokenImageUrl(tokenUri)
            urls.add(imageUrl
                ?: "")
        }

        model.tokenUrlList.postValue(urls)
    }

    Column {
        Text(
            text = symbol,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            //textAlign = TextAlign.End,
        )
        Spacer(modifier = Modifier.padding(8.dp))

        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            tokenUrlList.value?.forEachIndexed { index, url ->
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
                                contentDescription = artworks[index].title,
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
                                    text = artworks[index].title,
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
                                        text = artworks[index].artist,
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
}


@Preview
@Composable
private fun PreviewTokens() {
    val modelModule = module {
        single<AccountRepository> {
            InspectionModeAccountRepository(
                mutableListOf(
                    EtherAccount(
                        name = "john",
                        address = "0x000000",
                        privateKey = "0x11111",
                        isDefault = true,
                    ),
                ),
            )
        }

        single<Dragon721Service> {
            InspectionModeDragon721Service()
        }

        viewModel {
            EtherViewModel(get(), get())
        }
    }

    val context = LocalContext.current

    startKoin {
        androidLogger()
        androidContext(context)
        modules(modelModule)
    }

    val model = getViewModel<EtherViewModel>()

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides model,
    ) {
        Dragon721TokensSubScreen(
            defaultAccount = EtherAccount(
                name = "john",
                address = "0x00",
                privateKey = "0x00",
                isDefault = true,
            ),
            symbol = "symbol",
            artworks = listOf(
                Contracts_Dragon721_sol_Dragon721.Artwork(
                    "one star",
                    "john",
                    "bafyreieptcrsawxiyqw75yc5mmgewmosneyohacc6mfzblivj3yiqrz3zq",
                ),
            ),
            onLoadContract = {},
        )
    }
}

