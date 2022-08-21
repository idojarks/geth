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
import kotlinx.coroutines.coroutineScope
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
    val reloadAccounts = MutableLiveData(true)
    val artworks = MutableLiveData<List<Contracts_Dragon721_sol_Dragon721.Artwork>>(emptyList())

    suspend fun loadAccounts() = coroutineScope {
        val list = accountRepository.getAccounts()
        accounts.postValue(list)
        reloadAccounts.postValue(false)
    }

    fun addAccount(account: EtherAccount) {
        accounts.value = accountRepository.addAccount(account)
        loadDefaultAccount()
    }

    fun deleteAccount(account: EtherAccount) {
        accountRepository.deleteAccount(account)
        loadDefaultAccount()

        reloadAccounts.value = true
    }

    fun editAccount(
        srcAccount: EtherAccount,
        dstAccount: EtherAccount,
    ) {
        accounts.value = accountRepository.editAccount(srcAccount, dstAccount)
        loadDefaultAccount()
    }

    fun getAccount(address: String): EtherAccount? {
        return accountRepository.getAccounts()
            .find {
                it.address == address
            }
    }

    fun setDefaultAccount(account: EtherAccount) {
        accountRepository.setDefaultAccount(account)
        loadDefaultAccount()

        reloadAccounts.value = true
    }

    fun loadDefaultAccount() {
        defaultAccount.value = accountRepository.getAccounts()
            .find {
                it.isDefault
            }
    }

    fun loadContract(account: EtherAccount) {
        dragon721Service.loadContract(
            contractAddress = EtherUrl.contractAddress,
            privateKey = account.privateKey,
        )
    }

    fun loadArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> {
        val result = dragon721Service.getAllArtworks()
        artworks.value = result
        return result
    }

    fun getArtwork(index: Int): Result<Contracts_Dragon721_sol_Dragon721.Artwork?> {
        if (artworks.value?.isEmpty() == true) {
            loadArtworks()
        }

        return kotlin.runCatching {
            artworks.value?.elementAt(index)
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
            InspectionModeAccountRepository(
                mutableListOf(
                    EtherAccount(
                        name = "john",
                        address = "0x000000",
                        privateKey = "0x11111",
                        isDefault = true,
                    ),
                    EtherAccount(
                        name = "yong",
                        address = "0x000000",
                        privateKey = "0x11111",
                        isDefault = false,
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