package com.example.geth.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.Dragon721Service
import com.example.geth.service.blockchain.InspectionModeDragon721Service
import com.example.geth.service.contract.ContractInspectionModeRepository
import com.example.geth.service.contract.ContractRepository
import kotlinx.coroutines.coroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class EtherViewModel(
    val accountRepository: AccountRepository,
    val contractRepository: ContractRepository,
    val dragon721Service: Dragon721Service,
) : ViewModel() {
    val accounts = MutableLiveData<MutableList<EtherAccount>>(mutableListOf())
    val defaultAccount = MutableLiveData<EtherAccount>()
    val defaultContract = MutableLiveData<EtherContract>()
    val tokenUrlList = MutableLiveData(mutableListOf<String>())

    //val openAccountScreen = MutableLiveData(false)
    val artworks = MutableLiveData<List<Contracts_Dragon721_sol_Dragon721.Artwork>>(emptyList())
    val reloadAccounts = MutableLiveData(true)

    val contracts = MutableLiveData<MutableList<EtherContract>>(mutableListOf())

    suspend fun loadAccounts() = coroutineScope {
        accounts.postValue(accountRepository.accounts)
        reloadAccounts.postValue(false)
    }
/*
    fun addAccount(account: EtherAccount) {
        accounts.value = accountRepository.addAccount(account)
        loadDefaultAccount()
    }

 */

    fun deleteAccount(account: EtherAccount) {
        accountRepository.deleteAccount(account)
        loadDefaultAccount()

        reloadAccounts.value = true
    }
/*
    fun editAccount(
        srcAccount: EtherAccount,
        dstAccount: EtherAccount,
    ) {
        accounts.value = accountRepository.editAccount(srcAccount, dstAccount)
        loadDefaultAccount()
    }

 */

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

    fun loadDefaultAccount() {
        defaultAccount.value = accountRepository.getDefault()
    }

    fun loadDefaultContract() {
        defaultContract.value = contractRepository.getDefault()
    }

    fun loadContract(): Boolean {
        val defaultAccount = accountRepository.getDefault()
            ?: return false
        val defaultContract = contractRepository.getDefault()
            ?: return false

        dragon721Service.loadContract(
            contractAddress = defaultContract.address,
            privateKey = defaultAccount.privateKey,
        )

        return true
    }

    fun loadArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> {
        return dragon721Service.getAllArtworks()
    }

    fun loadDefaultAccountInCoroutine() {
        accountRepository.accounts
            .find {
                if (it.isDefault) {
                    println("default account loaded : $it")
                }

                it.isDefault
            }
            ?.let {
                defaultAccount.postValue(it)
            }
    }
}

val LocalEtherViewModelProvider = compositionLocalOf<EtherViewModel> {
    error("LocalEtherViewModelProvider not provide default factory")
}

@Composable
fun getInspectionModeViewModel(): EtherViewModel {
    val modelModule = module {
        single<AccountRepository> {
            InspectionModeAccountRepository()
        }

        single<ContractRepository> {
            ContractInspectionModeRepository()
        }

        single<Dragon721Service> {
            InspectionModeDragon721Service()
        }

        viewModel {
            EtherViewModel(get(), get(), get())
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