package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import java.math.BigInteger

class Dragon721ContractService : Web3ContractService<Contracts_Dragon721_sol_Dragon721, Artwork>() {
    lateinit var contract: Contracts_Dragon721_sol_Dragon721

    override val symbol: String by lazy {
        contract.symbol()
            .send()
    }

    @Suppress("UNCHECKED_CAST")
    override val artworks: List<Artwork> by lazy {
        contract.allArtworks.send() as List<Artwork>
    }

    override fun load(
        contractAddress: String,
        accountPrivateKey: String,
    ) {
        contract = Dragon721ContractLoader.load(
            web3j = web3,
            contractAddress = contractAddress,
            accountPrivateKey = accountPrivateKey,
        )
    }

    override fun ownerOf(tokenId: Long): String {
        return contract.ownerOf(BigInteger.valueOf(tokenId))
            .send()
    }

    override fun tokenUri(tokenId: Long): String {
        return contract.tokenURI(BigInteger.valueOf(tokenId))
            .send()
    }
/*
    override fun downloadToken(tokenId: BigInteger) : String {
        val uri = contract.tokenURI(tokenId)
            .send()

        val metadata = "$uri/metadata.json"

        HttpClient.getJson(metadata)
            ?.let {
                val lastSlashIndex = uri.lastIndexOf('/')
                val baseUrl = uri.slice(IntRange(0, lastSlashIndex))
                val imageUri = it.get("image")
                    .toString()
                    .replace("ipfs://", baseUrl)

                return HttpClient.downloadImage(imageUri, "image")
            }

        return ""
    }

 */
}