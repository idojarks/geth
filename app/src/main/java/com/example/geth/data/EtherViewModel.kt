package com.example.geth.data

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.blockchain.Dragon721Service

class EtherViewModel(
    val accountRepository: AccountRepository,
    val dragon721Service: Dragon721Service,
) : ViewModel() {
    val accounts = MutableLiveData<List<EtherAccount>>()
    val defaultAccount = MutableLiveData<EtherAccount>()
    val isChangeDefaultAccount = MutableLiveData(false)
    val tokenSymbol = MutableLiveData("")
    val tokenUrlList = MutableLiveData(mutableListOf<String>())

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
        defaultAccount.value = accountRepository.getAccounts()
            .find {
                it.isDefault
            }
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
