package com.example.geth.ui.screen.home.route.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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
fun Web3Info() {
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
                MutableStateFlow("")
            }
            val web3Version by flow.collectAsState()
            val scope = rememberCoroutineScope()

            scope.launch(Dispatchers.IO) {
                flow.emit(model.dragon721Service.getVersion())
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

            if (web3Version.isEmpty()) {
                val size = with(LocalDensity.current) {
                    val size = MaterialTheme.typography.titleMedium.fontSize
                    size.toDp()
                }

                CircularProgressIndicator(modifier = Modifier.size(size))
            }
            else {
                Text(
                    text = web3Version,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
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
        Web3Info()
    }
}
