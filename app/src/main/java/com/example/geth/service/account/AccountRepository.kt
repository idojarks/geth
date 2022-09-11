package com.example.geth.service.account

import com.example.geth.data.EtherAccount

interface AccountRepository {
    val accounts: MutableList<EtherAccount>

    fun addAccount(account: EtherAccount): List<EtherAccount>
    fun deleteAccount(account: EtherAccount): List<EtherAccount>
    fun editAccount(srcAccount: EtherAccount, dstAccount: EtherAccount): List<EtherAccount>
    fun setDefault(account: EtherAccount)
    fun getDefault(): EtherAccount?
}

infix fun AccountRepository.add(account: EtherAccount): List<EtherAccount> {
    return addAccount(account)
}

infix fun AccountRepository.delete(account: EtherAccount): List<EtherAccount> {
    return deleteAccount(account)
}