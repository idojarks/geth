package com.example.geth.ui.screen.home.route.contract.route

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.common.AddressBasedCard
import com.example.geth.ui.screen.common.FullScreenList
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AllContractsScreen(
    navController: NavController,
) {
    val model = LocalEtherViewModelProvider.current

    val contracts = model.contracts.observeAsState()
    val progressStateFlow = remember {
        MutableStateFlow(true)
    }
    val showProgressIndicator by progressStateFlow.collectAsState()

    LaunchedEffect(key1 = contracts) {
        model.contracts.value?.let {
            if (it.isEmpty()) {
                val contractsFromRepo = model.contractRepository.contracts

                if (contractsFromRepo.isEmpty()) {
                    progressStateFlow.emit(false)
                }
                else {
                    model.contracts.postValue(contractsFromRepo)
                }
            }
            else {
                progressStateFlow.emit(false)
            }
        }
    }

    FullScreenList(title = stringResource(id = R.string.nav_all_contracts), onClickAddButton = {
        navController.navigate("contract")
    }, lazyColumnContent = { lazyListScope ->
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
                )
            }
        }
    }, showProgressIndicator = showProgressIndicator, isEmpty = contracts.value?.isEmpty()
        ?: true)
}

@Preview
@Composable
private fun preview() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        AllContractsScreen(
            rememberNavController(),
        )
    }
}