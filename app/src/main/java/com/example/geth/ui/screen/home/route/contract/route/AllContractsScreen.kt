package com.example.geth.ui.screen.home.route.contract.route

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.data.EtherContract
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.common.AddressBasedCard
import com.example.geth.ui.screen.common.FullScreenList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun AllContractsScreen(
    navController: NavController,
) {
    val model = Dragon721ViewModelProvider.current

    val contracts = model.contracts.observeAsState()
    val progressStateFlow = remember {
        MutableStateFlow(true)
    }
    val showProgressIndicator by progressStateFlow.collectAsState()

    LaunchedEffect(key1 = contracts) {
        launch(Dispatchers.IO) {
            model.contracts.postValue(model.contractRepository.contracts)
            progressStateFlow.emit(false)
        }
    }

    FullScreenList(
        title = stringResource(id = R.string.nav_all_contracts),
        onClickAddButton = {
            navController.navigate("contract")
        },
        lazyColumnContent = { lazyListScope ->
            contracts.value?.forEach { contract ->
                lazyListScope.item {
                    AddressBasedCard(
                        name = contract.name,
                        address = contract.address,
                        privateKey = null,
                        isDefault = contract.isDefault,
                        onClickDefaultButton = {
                            model.contractRepository.setDefault(contract)
                        },
                        onClickEditMenu = {
                            navController.navigate(
                                route = "contract?address=${contract.address}",
                            )
                        },
                        onClickDeleteMenu = {
                            model.contractRepository.delete(contract)
                        },
                        extraContents = {
                            ContractSymbol(contract = contract)
                        },
                    )
                }
            }
        },
        showProgressIndicator = showProgressIndicator,
        isEmpty = contracts.value?.isEmpty()
            ?: true,
    )
}

@Composable
fun ContractSymbol(
    contract: EtherContract,
) {
    val model = Dragon721ViewModelProvider.current

    val symbolFlow = remember {
        MutableStateFlow("")
    }
    val symbol by symbolFlow.collectAsState()

    LaunchedEffect(key1 = contract, key2 = contract.isLoaded) {
        if (contract.isLoaded) {
            launch(Dispatchers.IO) {
                symbolFlow.emit(model.web3ContractService.symbol)
            }
        }
    }

    Text(
        text = "Symbol",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary,
    )

    if (symbol.isEmpty()) {
        val size = with(LocalDensity.current) {
            val size = MaterialTheme.typography.titleMedium.fontSize
            size.toDp()
        }

        CircularProgressIndicator(modifier = Modifier.size(size))
    }
    else {
        Text(
            text = symbol,
            style = MaterialTheme.typography.titleMedium,
        )
    }

    Spacer(
        modifier = Modifier.padding(4.dp),
    )
}

@Preview
@Composable
private fun preview() {
    CompositionLocalProvider(
        Dragon721ViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        AllContractsScreen(
            rememberNavController(),
        )
    }
}