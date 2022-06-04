package com.example.geth.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geth.Ether

class EtherViewModel :
    ViewModel() {
    val ether =
        Ether()
    val buildModel =
        MutableLiveData(
            ""
        )
    val web3ClientVersion =
        MutableLiveData(
            ""
        )
    val savedAccounts = MutableLiveData<List<String>>()

    init {
        ether.init(
            "https://ropsten.infura.io/v3/c7c3743a100841048f439743d078ea0d",
            this
        )


    }
}
