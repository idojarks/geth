package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import java.math.BigInteger

class InspectionModeWeb3ContractService() : Web3ContractService<Contracts_Dragon721_sol_Dragon721, Contracts_Dragon721_sol_Dragon721.Artwork>() {
    override val symbol: String = "dragon721"

    override val artworks: List<Contracts_Dragon721_sol_Dragon721.Artwork> = listOf(Contracts_Dragon721_sol_Dragon721.Artwork(
        "title",
        "artist",
        "123",
        "456",
        "9090",
        BigInteger.valueOf(100),
        "desc",
    ))

    override fun load(contractAddress: String, accountPrivateKey: String) {
        TODO("Not yet implemented")
    }

    override fun tokenUri(tokenId: Long): String = "https://ipfs.io/ipfs/bafyreieptcrsawxiyqw75yc5mmgewmosneyohacc6mfzblivj3yiqrz3zq"

    override fun ownerOf(tokenId: Long): String = "0x123456789"
}