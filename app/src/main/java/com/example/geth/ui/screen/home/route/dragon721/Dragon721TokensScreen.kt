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
fun Dragon721TokensScreen(
    defaultAccount: EtherAccount?,
) {
    val model = LocalEtherViewModelProvider.current

    val loadingStateFlow = remember {
        MutableStateFlow("")
    }
    val loadingState by loadingStateFlow.collectAsState()

    val artworkTokenListStateFlow = remember {
        MutableStateFlow(listOf<ArtworkToken>())
    }
    val artworkTokenList by artworkTokenListStateFlow.collectAsState()

    LaunchedEffect(key1 = defaultAccount) {
        defaultAccount?.let { account ->
            loadingStateFlow.emit("Loading contract")
            delay(1)
            model.loadContract(account)

            loadingStateFlow.emit("Loading artworks")
            delay(1)

            val list = mutableListOf<ArtworkToken>()

            model.artworks.forEachIndexed { index, artwork ->
                list.add(ArtworkToken(
                    index = index,
                    context = artwork,
                ))
            }

            artworkTokenListStateFlow.emit(list)
        }
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

