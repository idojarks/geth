package com.example.geth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.http.Url

interface EtherViewModelInterface {
    var ether: Ether?
    val buildModel: MutableLiveData<String>
    val web3ClientVersion: MutableLiveData<String>
    val accounts: MutableLiveData<MutableList<EtherAccount>>

    fun init(): Boolean
    fun addAccount(block: () -> EtherAccount)
}

class EtherViewModel : ViewModel(), EtherViewModelInterface {
    companion object {
        val previewViewModel = object : EtherViewModelInterface {
            override var ether: Ether? = null
            override val buildModel: MutableLiveData<String> = MutableLiveData("test1")
            override val web3ClientVersion: MutableLiveData<String> = MutableLiveData("test2")
            override val accounts: MutableLiveData<MutableList<EtherAccount>> = MutableLiveData(mutableListOf(EtherAccount("john", "0x00", "0x00")))

            override fun init(): Boolean {
                return true
            }

            override fun addAccount(block: () -> EtherAccount) {
            }
        }
    }

    override var ether: Ether? = null
    override val buildModel = MutableLiveData("")
    override val web3ClientVersion = MutableLiveData("")
    override val accounts = MutableLiveData<MutableList<EtherAccount>>()

    override fun addAccount(block: () -> EtherAccount) {
        accounts.value?.add(block())
    }

    override fun init(): Boolean {
        ether = Ether()
        ether?.init(Url().infuraRopsten, this)
        return true
    }


}
