package com.example.geth.ui

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.geth.Accounts
import com.example.geth.Ether
import kotlinx.coroutines.flow.Flow

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
