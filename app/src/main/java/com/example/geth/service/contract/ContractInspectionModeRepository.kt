package com.example.geth.service.contract

import com.example.geth.data.EtherContract

class ContractInspectionModeRepository : ContractRepository {
    override val contracts
        get() = mutableListOf(EtherContract(
            name = "first",
            address = "0xfirst",
            isDefault = true,
        ))

    override fun add(contract: EtherContract): Boolean {
        return true
    }

    override fun delete(contract: EtherContract): Boolean {
        return true
    }

    override fun replace(old: EtherContract, new: EtherContract, save: Boolean): Boolean {
        return true
    }

    override fun setDefault(contract: EtherContract): Boolean {
        return true
    }

    override fun getDefault(): EtherContract? {
        TODO("Not yet implemented")
    }

    override fun get(address: String): EtherContract? {
        TODO("Not yet implemented")
    }
}