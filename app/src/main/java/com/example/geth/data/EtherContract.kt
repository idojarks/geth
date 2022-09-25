package com.example.geth.data

data class EtherContract(
    val name: String,
    val address: String,
    val isDefault: Boolean,
    var isLoaded: Boolean = false,
)
