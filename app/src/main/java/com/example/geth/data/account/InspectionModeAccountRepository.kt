package com.example.geth.data.account

class InspectionModeAccountRepository(
    private val accounts: MutableList<EtherAccount>,
) : AccountRepository {
    override fun getAccounts(): List<EtherAccount> {
        return accounts
    }

    override fun addAccount(account: EtherAccount): List<EtherAccount> {
        accounts.add(account)
        return accounts
    }

    override fun deleteAccount(account: EtherAccount): List<EtherAccount> {
        accounts.remove(account)
        return accounts
    }
}