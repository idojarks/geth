package com.example.geth.data

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Ether
import com.example.geth.MyApplication
import com.example.geth.http.Url

interface EtherViewModelInterface {
    var ether: Ether?
    val buildModel: MutableLiveData<String>
    val web3ClientVersion: MutableLiveData<String>
    val accounts: MutableLiveData<List<EtherAccount>>

    fun init(): Boolean

    fun loadAccounts()
    fun addAccount(block: () -> EtherAccount)
    fun deleteAccount(account: EtherAccount)

    fun getBalance(account: EtherAccount): String
}

class EtherViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel(), EtherViewModelInterface {
    companion object {
        val previewViewModel = object : EtherViewModelInterface {
            override var ether: Ether? = null
            override val buildModel: MutableLiveData<String> = MutableLiveData("test1")
            override val web3ClientVersion: MutableLiveData<String> = MutableLiveData("test2")
            override val accounts: MutableLiveData<List<EtherAccount>> = MutableLiveData(listOf(EtherAccount("john", "0x00", "0x00")))

            override fun init(): Boolean {
                return true
            }

            override fun loadAccounts() {
            }

            override fun addAccount(block: () -> EtherAccount) {
            }

            override fun deleteAccount(account: EtherAccount) {
            }

            override fun getBalance(account: EtherAccount): String = ""
        }
    }

    override var ether: Ether? = null
    override val buildModel = MutableLiveData("")
    override val web3ClientVersion = MutableLiveData("")
    override val accounts = MutableLiveData<List<EtherAccount>>()

    override fun init(): Boolean {
        ether = Ether()
        ether?.init(Url().infuraRopsten, this)

        return true
    }

    override fun loadAccounts() {
        accounts.value = accountRepository.getAccounts()
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
}

val LocalEtherViewModelProvider = compositionLocalOf {
    EtherViewModel(AccountRepository(
        MyApplication.getContext(),
        "AccountTable",
    ))
    //{ error("CompositionLocal LocalEtherViewModelProvider not present") }
}
