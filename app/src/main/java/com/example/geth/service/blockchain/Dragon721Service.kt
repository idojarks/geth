package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherContract
import org.web3j.tx.Contract
import java.math.BigInteger

interface Dragon721Service {
    fun getVersion(): String
    fun getAccounts(): List<String>
    fun getBalance(address: String): String
    fun loadContract(
        contract: EtherContract,
        account: EtherAccount,
    )

    fun ownerOf(tokenId: Long): Result<String>

    fun getSymbol(contract: Contract): String
    fun getTokenUri(tokenId: Long): String
    fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork>
    suspend fun downloadToken(
        tokenId: BigInteger,
        callback: (String) -> Unit,
    )
}