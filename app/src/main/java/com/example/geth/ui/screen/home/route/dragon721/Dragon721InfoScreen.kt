package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dragon721InfoScreen() {
    val model = LocalEtherViewModelProvider.current
    val rootNavController = RootNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.nav_info))
                },
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            val flow = remember {
                MutableStateFlow(Pair("", ""))
            }
            val flowState = flow.collectAsState()
            val (version, symbol) = flowState.value

            LaunchedEffect(key1 = model.contractRepository.getDefault()) {
                val contract = model.contractRepository.getDefault()

                if (contract?.web3Contract == null) {
                    flow.emit(Pair("", ""))
                    return@LaunchedEffect
                }

                launch(Dispatchers.IO) {
                    contract.web3Contract?.let {
                        val web3Version = model.dragon721Service.getVersion()
                        val tokenSymbol = model.dragon721Service.getSymbol(it)

                        flow.emit(Pair(web3Version, tokenSymbol))
                    }
                }
            }

            Text(
                text = "Web3",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Version",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = version,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.padding(14.dp))

            Text(
                text = "Token",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Symbol",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        Dragon721InfoScreen()
    }
}
