package com.example.geth.data

import org.web3j.tx.Contract

data class EtherContract(
    val name: String,
    val address: String,
    val isDefault: Boolean,
    var web3Contract: Contract? = null,
)
