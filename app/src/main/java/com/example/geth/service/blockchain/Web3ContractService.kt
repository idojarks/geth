package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.RealtimeGasProvider
import com.example.geth.data.EtherUrl
import com.example.geth.service.http.HttpClient
import com.example.geth.web3.Web3Utils
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert

abstract class Web3ContractService<T, out R> {
    protected val web3: Web3j by lazy {
        val url = StringBuilder(EtherUrl.alchemy_goerli)

        if (url.isEmpty()) {
            val newEtherUrl = EtherUrl.getLocalEthUrl()
            url.append(newEtherUrl)
        }

        Web3j.build(HttpService(url.toString(), HttpClient.instance))
    }

    val version: String by lazy {
        web3.web3ClientVersion()
            .send().web3ClientVersion
    }

    val accounts: List<String> by lazy {
        web3.ethAccounts()
            .send().accounts
    }

    abstract val symbol: String

    abstract val artworks: List<R>

    fun balance(accountAddress: String): String {
        if (!Web3Utils.isAddress(accountAddress)) {
            return "invalid address"
        }

        val balance = web3.ethGetBalance(accountAddress, DefaultBlockParameterName.LATEST)
            .send().balance
        return Convert.fromWei(balance.toString(), Convert.Unit.ETHER)
            .toString()
    }

    abstract fun load(
        contractAddress: String,
        accountPrivateKey: String,
    )

    abstract fun ownerOf(tokenId: Long): String

    abstract fun tokenUri(tokenId: Long): String

    //abstract fun downloadToken(tokenId: BigInteger) : String
}

interface ContractLoader<T> {
    fun load(
        web3j: Web3j,
        contractAddress: String,
        accountPrivateKey: String,
    ): T
}

object Dragon721ContractLoader : ContractLoader<Contracts_Dragon721_sol_Dragon721> {
    override fun load(
        web3j: Web3j,
        contractAddress: String,
        accountPrivateKey: String,
    ): Contracts_Dragon721_sol_Dragon721 {
        val credentials = Credentials.create(accountPrivateKey)
        val gasProvider = RealtimeGasProvider()

        return Contracts_Dragon721_sol_Dragon721.load(
            contractAddress,
            web3j,
            credentials,
            gasProvider,
        )
    }
}