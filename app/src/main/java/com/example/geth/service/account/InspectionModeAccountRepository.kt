package com.example.geth.service.account

import com.example.geth.data.EtherAccount

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

    override fun setDefaultAccount(account: EtherAccount): List<EtherAccount> {
        accounts.find {
            it == account
        }
            ?.apply {
                isDefault = true
            }
        return accounts
    }
}