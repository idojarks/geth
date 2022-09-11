package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geth.data.ArtworkToken
import com.example.geth.data.EtherAccount
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun Dragon721TokensScreen() {
    val model = LocalEtherViewModelProvider.current

    val loadingStateFlow = remember {
        MutableStateFlow("")
    }
    val loadingState by loadingStateFlow.collectAsState()

    val artworkTokenListStateFlow = remember {
        MutableStateFlow(listOf<ArtworkToken>())
    }
    val artworkTokenList by artworkTokenListStateFlow.collectAsState()

    val defaultAccount = model.accountRepository.getDefault()
    val defaultContract = model.contractRepository.getDefault()

    LaunchedEffect(
        key1 = defaultAccount,
        key2 = defaultContract,
    ) {
        if (defaultAccount == null || defaultContract == null) {
            return@LaunchedEffect
        }

        loadingStateFlow.emit("Loading contract")
        delay(1)
        model.loadContract()

        loadingStateFlow.emit("Loading artworks")
        delay(1)

        model.loadArtworks()
            .run {
                val list = mutableListOf<ArtworkToken>()

                forEachIndexed { index, artwork ->
                    ArtworkToken(
                        index = index,
                        context = artwork,
                    ).run {
                        list.add(this)
                    }
                }

                artworkTokenListStateFlow.emit(list)
            }
    }

    if (defaultAccount == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Set default account")
        }

        return
    }

    if (defaultContract == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Set default contract")
        }

        return
    }

    if (artworkTokenList.isEmpty()) {
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
                Text(text = loadingState)
            }
        }

        return
    }

    Dragon721ArtworksScreen(artworkTokenList = artworkTokenList)
}

@Preview
@Composable
private fun PreviewTokens() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
    ) {
        Dragon721TokensScreen()
    }
}

