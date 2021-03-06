package com.example.geth.data.account

interface AccountRepository {
    fun getAccounts(): List<EtherAccount>
    fun addAccount(account: EtherAccount): List<EtherAccount>
    fun deleteAccount(account: EtherAccount): List<EtherAccount>
}

infix fun AccountRepository.add(account: EtherAccount): List<EtherAccount> {
    return addAccount(account)
}

infix fun AccountRepository.delete(account: EtherAccount): List<EtherAccount> {
    return deleteAccount(account)
}