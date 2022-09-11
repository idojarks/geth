package com.example.geth.data

object EtherUrl {
    const val gasPrice = "https://api.gasprice.io/v1/estimates/"
    const val infuraRopsten = "https://ropsten.infura.io/v3/c7c3743a100841048f439743d078ea0d"

    //const val contractAddress = "0x983EAD129Eb9Ee8577A7f6E15f66F282E1f42dC7"
    const val alchemy_api_key = "oLOVGkd0eokblH-yK32lH1bBzZxk2Tsc"
    const val alchemy_goerli = "https://eth-goerli.g.alchemy.com/v2/$alchemy_api_key"


    fun getLocalEthUrl(): String {
        // "http://10.0.2.2:8545" <- 에뮬레이터에서 PC의 localhost로 접속
        return if (android.os.Build.MODEL.contains("emulator", true)) "http://10.0.2.2:8545" else "http://221.138.108.203:8545"
    }
}

