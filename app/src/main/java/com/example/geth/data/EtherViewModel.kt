package com.example.geth.data

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Ether
import com.example.geth.data.account.AccountRepository
import com.example.geth.data.account.EtherAccount
import com.example.geth.http.Url

interface EtherViewModelInterface {
    var ether: Ether?
    val buildModel: MutableLiveData<String>
    val web3ClientVersion: MutableLiveData<String>
    val accounts: MutableLiveData<List<EtherAccount>>

    fun init(): Boolean

    fun loadAccounts(): List<EtherAccount>
    fun addAccount(block: () -> EtherAccount)
    fun deleteAccount(account: EtherAccount)

    fun getBalance(account: EtherAccount): String
}

class EtherViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel(), EtherViewModelInterface {
    /*
    companion object {
        val previewViewModel = object : EtherViewModelInterface {
            override var ether: Ether? = null
            override val buildModel: MutableLiveData<String> = MutableLiveData("test1")
            override val web3ClientVersion: MutableLiveData<String> = MutableLiveData("test2")
            override val accounts: MutableLiveData<List<EtherAccount>> = MutableLiveData(listOf(EtherAccount("john", "0x00", "0x00")))

            override fun init(): Boolean {
                return true
            }

            override fun loadAccounts(): List<EtherAccount> {
                return emptyList()
            }

            override fun addAccount(block: () -> EtherAccount) {
            }

            override fun deleteAccount(account: EtherAccount) {
            }

            override fun getBalance(account: EtherAccount): String = ""
        }
    }

     */

    override var ether: Ether? = null
    override val buildModel = MutableLiveData("")
    override val web3ClientVersion = MutableLiveData("")
    override val accounts = MutableLiveData<List<EtherAccount>>()

    override fun init(): Boolean {
        ether = Ether()
        ether?.init(Url().infuraRopsten, this)

        return true
    }

    override fun loadAccounts(): List<EtherAccount> {
        val list = accountRepository.getAccounts()
        accounts.value = list
        return list
    }

    override fun addAccount(block: () -> EtherAccount) {
        accounts.value = accountRepository.addAccount(block())
    }

    override fun deleteAccount(account: EtherAccount) {
        accounts.value = accountRepository.deleteAccount(account)
    }

    override fun getBalance(account: EtherAccount): String {
        return checkNotNull(ether).getBalance(account.address)
    }

    fun getDefaultAccount(): EtherAccount? {
        return accountRepository.getAccounts()
            .find {
                it.isDefault
            }
    }
}

val LocalEtherViewModelProvider = compositionLocalOf<EtherViewModel> {
    error("LocalEtherViewModelProvider not provide default factory")
}
