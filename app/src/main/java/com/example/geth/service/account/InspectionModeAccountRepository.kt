package com.example.geth.service.account

import com.example.geth.data.EtherAccount

class InspectionModeAccountRepository : AccountRepository {
    override val accounts: MutableList<EtherAccount> = mutableListOf(EtherAccount(
        name = "test",
        address = "0xaddress",
        privateKey = "0xprivatekey",
        isDefault = true,
    ))

    override fun addAccount(account: EtherAccount): List<EtherAccount> {
        accounts.add(account)
        return accounts
    }

    override fun deleteAccount(account: EtherAccount): List<EtherAccount> {
        accounts.remove(account)
        return accounts
    }

    override fun editAccount(srcAccount: EtherAccount, dstAccount: EtherAccount): List<EtherAccount> {
        return accounts
    }

    override fun setDefault(account: EtherAccount) {
    }

    override fun getDefault(): EtherAccount {
        return EtherAccount(
            name = "test",
            address = "0xaddress",
            privateKey = "0xprivateKey",
            isDefault = true,
        )
    }
}