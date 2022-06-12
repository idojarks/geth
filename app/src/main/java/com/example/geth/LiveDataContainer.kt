package com.example.geth

import androidx.lifecycle.MutableLiveData

class LiveDataContainer {
    companion object {
        lateinit var buildModelLiveData: MutableLiveData<String>
        lateinit var web3ClientVersionLiveData: MutableLiveData<String>
    }
}