package com.example.geth.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Ether
import com.example.geth.http.Url

class EtherViewModel : ViewModel() {
    private val ether = Ether()
    val buildModel = MutableLiveData("")
    val web3ClientVersion = MutableLiveData("")
    val savedAccounts = MutableLiveData<List<String>>()

    init {
        ether.init(Url().infuraRopsten, this)
    }
}
