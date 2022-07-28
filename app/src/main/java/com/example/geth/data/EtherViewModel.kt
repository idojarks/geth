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
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class EtherViewModel(
    val accountRepository: AccountRepository,
    val dragon721Service: Dragon721Service,
) : ViewModel() {
    val accounts = MutableLiveData<List<EtherAccount>>()
    val defaultAccount = MutableLiveData<EtherAccount>()
    val isChangeDefaultAccount = MutableLiveData(false)
    val tokenSymbol = MutableLiveData("")
    val tokenUrlList = MutableLiveData(mutableListOf<String>())
    val openAccountScreen = MutableLiveData(false)
    val artworks = MutableLiveData<List<Contracts_Dragon721_sol_Dragon721.Artwork>>(emptyList())
    val symbol = MutableLiveData("")

    fun loadAccounts(): List<EtherAccount> {
        val list = accountRepository.getAccounts()
        accounts.value = list
        return list
    }

    fun addAccount(block: () -> EtherAccount) {
        accounts.value = accountRepository.addAccount(block())
    }

    fun deleteAccount(account: EtherAccount) {
        accounts.value = accountRepository.deleteAccount(account)
    }

    fun loadDefaultAccount() {
        val account = accountRepository.getAccounts()
            .find {
                it.isDefault
            }
            ?: return

        defaultAccount.value = account
    }

    fun loadContract() {
        val account = checkNotNull(defaultAccount.value)

        dragon721Service.loadContract(
            contractAddress = EtherUrl.contractAddress,
            privateKey = account.privateKey,
        )

        artworks.value = dragon721Service.getAllArtworks()
        symbol.value = dragon721Service.getSymbol()
    }

    fun loadDefaultAccountInCoroutine() {
        accountRepository.getAccounts()
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

/*
    fun loadContract(
        contractAddress: String,
        privateKey: String,
    ): Contract {
        return dragon721Service.loadContract(
            contractAddress = contractAddress,
            privateKey = privateKey,
        )
    }

    fun getBalance(address: String): String {
        return dragon721Service.getBalance(address = address)
    }

    fun getSymbol(): String {
        return dragon721Service.getSymbol()
    }

    fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> {
        return dragon721Service.getAllArtworks()
    }

 */
}

val LocalEtherViewModelProvider = compositionLocalOf<EtherViewModel> {
    error("LocalEtherViewModelProvider not provide default factory")
}

@Composable
fun getInspectionModeViewModel(): EtherViewModel {
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

    return getViewModel()
}