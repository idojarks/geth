package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import java.math.BigInteger

interface Dragon721Service {
    fun getVersion(): String
    fun getAccounts(): List<String>
    fun getBalance(address: String): String
    fun loadContract(
        contractAddress: String,
        privateKey: String,
    )

    fun ownerOf(tokenId: Long): Result<String>

    fun getSymbol(): String
    fun getTokenUri(tokenId: Long): String
    fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork>
    suspend fun downloadToken(
        tokenId: BigInteger,
        callback: (String) -> Unit,
    )
}