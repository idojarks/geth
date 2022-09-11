package com.example.geth.service.contract

import com.example.geth.data.EtherContract

interface ContractRepository {
    val contracts: MutableList<EtherContract>

    fun add(contract: EtherContract): Boolean
    fun delete(contract: EtherContract): Boolean
    fun replace(old: EtherContract, new: EtherContract, save: Boolean = true): Boolean
    fun setDefault(contract: EtherContract): Boolean
    fun getDefault(): EtherContract?
    fun get(address: String): EtherContract?
}