package com.example.geth.ui.screen.home.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.Dragon721Service
import com.example.geth.service.blockchain.InspectionModeDragon721Service
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Composable
fun InfoScreen() {
    val model = LocalEtherViewModelProvider.current

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "web3 version: ${model.dragon721Service.getVersion()}")
    }
}

@Preview()
@Composable
private fun Preview() {
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
        InfoScreen()
    }
}

