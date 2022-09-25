package com.example.geth.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.InspectionModeWeb3ContractService
import com.example.geth.service.blockchain.Web3ContractService
import com.example.geth.service.contract.ContractInspectionModeRepository
import com.example.geth.service.contract.ContractRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class EtherViewModel<T, out R>(
    val accountRepository: AccountRepository,
    val contractRepository: ContractRepository,
    val web3ContractService: Web3ContractService<T, R>,
) : ViewModel() {
    val accounts = MutableLiveData<MutableList<EtherAccount>>(mutableListOf())
    private val defaultAccount = MutableLiveData<EtherAccount>()
    val clickedArtworkIndex = MutableLiveData(-1)
    private val reloadAccounts = MutableLiveData(true)
    val contracts = MutableLiveData<MutableList<EtherContract>>(mutableListOf())

    fun deleteAccount(account: EtherAccount) {
        accountRepository.deleteAccount(account)
        loadDefaultAccount()

        reloadAccounts.value = true
    }

    fun getAccount(address: String): EtherAccount? {
        return accountRepository.accounts.find {
            it.address == address
        }
    }

    fun setDefaultAccount(account: EtherAccount) {
        accountRepository.setDefault(account)
        loadDefaultAccount()

        reloadAccounts.value = true
    }

    private fun loadDefaultAccount() {
        defaultAccount.value = accountRepository.getDefault()
    }
}

val Dragon721ViewModelProvider = compositionLocalOf<EtherViewModel<Contracts_Dragon721_sol_Dragon721, Artwork>> {
    error("Dragon721ViewModelProvider not provide default factory")
}

@Composable
fun getInspectionModeViewModel(): EtherViewModel<Contracts_Dragon721_sol_Dragon721, Artwork> {
    val modelModule = module {
        single<AccountRepository> {
            InspectionModeAccountRepository()
        }

        single<ContractRepository> {
            ContractInspectionModeRepository()
        }

        single<Web3ContractService<Contracts_Dragon721_sol_Dragon721, Artwork>> {
            InspectionModeWeb3ContractService()
        }

        viewModel {
            EtherViewModel(get(), get(), get<Web3ContractService<Contracts_Dragon721_sol_Dragon721, Artwork>>())
        }
    }

    val context = LocalContext.current

    startKoin {
        androidLogger()
        androidContext(context)
        modules(modelModule)
    }

    return getViewModel()
}