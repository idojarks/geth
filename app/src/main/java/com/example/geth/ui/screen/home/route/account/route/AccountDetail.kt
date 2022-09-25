package com.example.geth.ui.screen.home.route.account.route

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModel
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.account.add
import com.example.geth.service.blockchain.InspectionModeWeb3ContractService
import com.example.geth.service.contract.ContractInspectionModeRepository
import com.example.geth.ui.screen.common.AddressBasedDetail

@Composable
fun AccountDetailScreen(
    navController: NavController,
    account: EtherAccount? = null,
) {
    val model = Dragon721ViewModelProvider.current

    val (title, doneButtonName) = account?.let {
        Pair(
            stringResource(id = R.string.edit_account),
            stringResource(id = R.string.update_contract),
        )
    }
        ?: Pair(
            stringResource(id = R.string.new_account),
            stringResource(id = R.string.register_account),
        )

    var name by remember {
        mutableStateOf(account?.name
            ?: "")
    }
    var address by remember {
        mutableStateOf(account?.address
            ?: "")
    }
    var privateKey by remember {
        mutableStateOf(account?.privateKey
            ?: "")
    }
    var isDefault by remember {
        mutableStateOf(account?.isDefault
            ?: false)
    }

    AddressBasedDetail(
        title = title,
        onClickBackButton = { navController.popBackStack() },
        name = name,
        onChangeName = {
            name = it
        },
        address = address,
        onChangeAddress = {
            address = it
        },
        privateKey = privateKey,
        onChangePrivateKey = {
            privateKey = it
        },
        isDefault = isDefault,
        onCheckedChangeDefault = {
            isDefault = it
        },
        onDone = {
            val newAccount = EtherAccount(
                name = name,
                address = address,
                privateKey = privateKey,
                isDefault = isDefault,
            )

            if (account == null) {
                model.accountRepository.add(newAccount)
            }
            else {
                model.accountRepository.editAccount(account, newAccount)
            }

            model.accounts.value = model.accountRepository.accounts

            navController.popBackStack()
        },
        doneButtonName = doneButtonName,
    )
}

@Preview
@Composable
private fun Preview() {
    val model = EtherViewModel(
        accountRepository = InspectionModeAccountRepository(),
        contractRepository = ContractInspectionModeRepository(),
        web3ContractService = InspectionModeWeb3ContractService(),
    )

    CompositionLocalProvider(
        Dragon721ViewModelProvider provides model,
    ) {
        val navController = rememberNavController()

        AccountDetailScreen(
            navController = navController,
        )
    }
}

