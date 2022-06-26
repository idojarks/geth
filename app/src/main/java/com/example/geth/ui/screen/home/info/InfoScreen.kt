package com.example.geth.ui.screen.home.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.account.EtherAccount
import com.example.geth.data.account.InspectionModeAccountRepository

@Composable
fun InfoScreen() {
    if (LocalInspectionMode.current) {
        return
    }

    val model = LocalEtherViewModelProvider.current
    val buildModel = model.buildModel.observeAsState("")
    val web3ClientVersion = model.web3ClientVersion.observeAsState("")

    rememberSaveable {
        model.init()
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "build model: ${buildModel.value}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "web3 client version: ${web3ClientVersion.value}")
    }
}

@Preview()
@Composable
private fun Preview() {
    val model = EtherViewModel(
        accountRepository = InspectionModeAccountRepository(mutableListOf(
            EtherAccount(
                name = "john",
                address = "0x000000",
                privateKey = "0x11111",
            ),
        )),
    )

    CompositionLocalProvider(LocalEtherViewModelProvider provides model) {
        InfoScreen()
    }
}

