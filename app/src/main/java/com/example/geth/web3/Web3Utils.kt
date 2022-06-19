package com.example.geth.web3

import org.web3j.crypto.WalletUtils

class Web3Utils {
    companion object {
        fun isAddress(address: String) = WalletUtils.isValidAddress(address)
        fun isPk(pk: String) = WalletUtils.isValidPrivateKey(pk)
    }
}