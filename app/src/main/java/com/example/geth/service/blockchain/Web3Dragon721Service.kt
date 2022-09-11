package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.RealtimeGasProvider
import com.example.geth.data.EtherUrl
import com.example.geth.service.http.HttpClient
import com.example.geth.web3.Web3Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import java.math.BigInteger

class Web3Dragon721Service : Dragon721Service {
    private val web3: Web3j
    private val accounts = mutableListOf<String>()
    private var dragon721Contract: Contracts_Dragon721_sol_Dragon721? = null

    init {
        val url = StringBuilder(EtherUrl.alchemy_goerli)

        if (url.isEmpty()) {
            val newEtherUrl = EtherUrl.getLocalEthUrl()
            url.append(newEtherUrl)
        }

        web3 = Web3j.build(HttpService(url.toString(), HttpClient.instance))
    }

    override fun getVersion(): String {
        return runCatching {
            runBlocking(Dispatchers.IO) {
                web3.web3ClientVersion()
                    .send().web3ClientVersion
            }
        }.getOrDefault("error")
    }

    override fun getAccounts(): List<String> {
        runBlocking {
            val channel = Channel<String>()

            launch(Dispatchers.IO) {
                web3.ethAccounts()
                    .flowable()
                    .subscribe({
                        it.accounts.forEachIndexed { index, account ->
                            launch {
                                channel.send(account)

                                if (index == it.accounts.lastIndex) {
                                    channel.close()
                                }
                            }
                        }
                    }, {
                        println(it)
                        channel.close()
                    })
            }

            for (account in channel) {
                accounts.add(account)
            }
        }

        return accounts.toList()
    }

    override fun getBalance(address: String): String {
        if (!Web3Utils.isAddress(address)) {
            return "-1"
        }

        return runBlocking {
            val channel = Channel<String>()

            launch(Dispatchers.IO) {
                web3.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                    .flowable()
                    .subscribe({ result ->
                        launch {
                            try {
                                val balance = Convert.fromWei(result.balance.toString(), Convert.Unit.ETHER)

                                channel.send(balance.toString())
                                channel.close()
                            }
                            catch (e: org.web3j.exceptions.MessageDecodingException) {
                            }
                        }
                    }, {
                        launch {
                            channel.send("-1")
                            channel.close()
                        }
                    })
                    .dispose()
            }

            return@runBlocking channel.receive()
        }
    }
/*
    fun loadDragon721Contract(context: Context): Contract? {
        val password = "1212"
        val filename = "wallet"
        val file = File(context.filesDir, filename)

        val provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
        if (provider.isNullOrEmpty()) {
            return null
        }

        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)

        if (!file.exists()) {
            try {
                WalletUtils.generateNewWalletFile(password, file)
            }
            catch (e: Exception) {
                println(e.message)
            }
        }

        val credentials = WalletUtils.loadCredentials(password, file.path)
        contract = Contracts_Dragon721_sol_Dragon721.load(
            Url.contractAddress,
            web3,
            credentials,
            RealtimeGasProvider(),
        )

        return contract
    }

 */

    override fun loadContract(
        contractAddress: String,
        privateKey: String,
    ) {
        val credentials = Credentials.create(privateKey)
        val gasProvider = RealtimeGasProvider()

        dragon721Contract = Contracts_Dragon721_sol_Dragon721.load(
            contractAddress,
            web3,
            credentials,
            gasProvider,
        )
    }

    override fun getSymbol(): String {
        val sb = StringBuilder()

        runBlocking(Dispatchers.IO) {
            checkNotNull(dragon721Contract).symbol()
                .flowable()
                .subscribe({
                    sb.append(it)
                }, {
                    sb.append(it.message)
                })
        }

        return sb.toString()
    }

    override fun getTokenUri(tokenId: Long): String {
        val sb = StringBuilder()

        runBlocking(Dispatchers.IO) {
            checkNotNull(dragon721Contract).tokenURI(BigInteger.valueOf(tokenId))
                .flowable()
                .subscribe({
                    sb.append(it)
                }, {
                    sb.append(it.message)
                })
        }

        return sb.toString()
    }

    override fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> {
        val artworks = mutableListOf<Contracts_Dragon721_sol_Dragon721.Artwork>()

        runBlocking(Dispatchers.IO) {
            checkNotNull(dragon721Contract).allArtworks.flowable()
                .subscribe({ list ->
                    list.forEach { item ->
                        item?.let {
                            val artwork = it as Contracts_Dragon721_sol_Dragon721.Artwork

                            artworks.add(artwork)
                        }
                    }
                }, {
                    println(it)
                })
        }

        return artworks
    }

    override suspend fun downloadToken(
        tokenId: BigInteger,
        callback: (String) -> Unit,
    ) = coroutineScope {
        checkNotNull(dragon721Contract).tokenURI(tokenId)
            .flowable()
            .subscribe({ tokenUri ->
                val metadata = "$tokenUri/metadata.json"

                HttpClient.getJson(metadata)
                    ?.let {
                        val lastSlashIndex = tokenUri.lastIndexOf('/')
                        val baseUrl = tokenUri.slice(IntRange(0, lastSlashIndex))
                        val imageUri = it.get("image")
                            .toString()
                            .replace("ipfs://", baseUrl)

                        val pathname = HttpClient.downloadImage(imageUri, "eye")
                        callback(pathname)
                    }
            }, {

            })
            .dispose()
    }

    override fun ownerOf(tokenId: Long): Result<String> {
        return runCatching {
            checkNotNull(dragon721Contract).ownerOf(BigInteger.valueOf(tokenId))
                .send()
        }
    }
}