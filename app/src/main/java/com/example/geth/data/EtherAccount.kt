package com.example.geth.data

data class EtherAccount(
    val name: String,
    val address: String,
    val privateKey: String,
    val isDefault: Boolean = false,
)
