package com.example.geth.ui.screen.home.route.contract.route

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.EtherContract
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.common.AddressBasedDetail

@Composable
fun ContractDetailScreen(
    navController: NavController,
    contract: EtherContract? = null,
) {
    val model = LocalEtherViewModelProvider.current

    val (title, doneButtonName) = contract?.let {
        Pair(stringResource(id = R.string.edit_contract), stringResource(id = R.string.update_contract))
    }
        ?: Pair(stringResource(id = R.string.new_contract), stringResource(id = R.string.register_contract))

    var name by remember {
        mutableStateOf(contract?.name)
    }
    var address by remember {
        mutableStateOf(contract?.address)
    }
    var isDefault by remember {
        mutableStateOf(contract?.isDefault
            ?: false)
    }

    AddressBasedDetail(
        title = title,
        onClickBackButton = {
            navController.popBackStack()
        },
        name = name
            ?: "",
        onChangeName = {
            name = it
        },
        address = address
            ?: "",
        onChangeAddress = {
            address = it
        },
        privateKey = null,
        onChangePrivateKey = null,
        isDefault = isDefault,
        onCheckedChangeDefault = {
            isDefault = it
        },
        onDone = {
            val newContract = EtherContract(
                name = checkNotNull(name),
                address = checkNotNull(address),
                isDefault = isDefault,
            )

            contract?.let {
                model.contractRepository.replace(it, newContract)
            }
                ?: model.contractRepository.add(newContract)

            navController.popBackStack()
        },
        doneButtonName = doneButtonName,
    )
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        ContractDetailScreen(rememberNavController(), EtherContract(
            name = "test",
            address = "0xtest",
            isDefault = true,
        ))
    }
}

