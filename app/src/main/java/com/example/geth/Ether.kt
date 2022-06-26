package com.example.geth

import android.content.Context
import com.example.geth.data.EtherViewModel
import com.example.geth.data.account.EtherAccount
import com.example.geth.http.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import org.web3j.utils.Convert
import java.io.File
import java.math.BigInteger
import java.security.Security

class Ether {
    private lateinit var web3: Web3j
    private val accounts = mutableListOf<String>()
    private var model: EtherViewModel? = null
    private val account1 = "0x9AaB9CAE534599A024fe48A5EE7331CDf2b7cCd9"
    private val account2 = "0x909E9cF3DF7C9f4E29315806741D007D7bbC97C2" // metamask pw:nemo5038
    private val contractAddress = "0x983EAD129Eb9Ee8577A7f6E15f66F282E1f42dC7"

    private lateinit var contract: Contract /*= Contracts_Dragon721_sol_Dragon721.load(
        contractAddress,
        Web3j.build(HttpService(HttpClient.instance)),
        Credentials.create("ef1162540ed5189c7f7eb5c9bf274767e95aad0559b3920eb3dc50c35aecd465"),
        StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT),
    )
    */

    private lateinit var dragon721Contract: Contracts_Dragon721_sol_Dragon721

    fun init(networkUrl: String? = null, viewModel: EtherViewModel? = null) {
        model = viewModel

        viewModel?.let {
            it.buildModel.value = android.os.Build.MODEL
        }

        runBlocking {
            val channel = Channel<String>()

            launch(Dispatchers.IO) {
                val url = networkUrl
                    ?: if (android.os.Build.MODEL.contains("emulator", true)) "http://10.0.2.2:8545" else "http://221.138.108.203:8545" //"http://10.0.2.2:8545" <- 에뮬레이터에서 PC의 localhost로 접속

                web3 = Web3j.build(HttpService(url, HttpClient.instance))
                    .also {
                        it.web3ClientVersion()
                            .flowable()
                            .subscribe({
                                launch {
                                    channel.send(it.web3ClientVersion)
                                    channel.close()
                                }
                            }, {
                                launch {
                                    channel.send(it.message
                                        ?: "unknown error.")
                                    channel.close()
                                }
                            })
                            .dispose()
                    }
            }

            for (msg in channel) {
                viewModel?.let {
                    it.web3ClientVersion.value = msg
                }
            }
        }

        println("ether initialized.")
    }

    fun getAccounts(): List<String> {
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

    fun getBalance(address: String): String {
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
                            } catch (e: org.web3j.exceptions.MessageDecodingException) {
                                val sa = SavedAccount()
                                val context = MyApplication.getContext()

                                sa.delete(context, address)

                                val accounts = sa.load(context)

                                model?.let { model ->
                                    launch(Dispatchers.Main) {
                                        accounts.forEach { address ->
                                            model.addAccount {
                                                EtherAccount(
                                                    name = "john",
                                                    address = address,
                                                    privateKey = "",
                                                )
                                            }
                                        }
                                    }
                                }
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

    fun loadContract(context: Context): Contract? {
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
            } catch (e: Exception) {
                println(e.message)
            }
        }

        val credentials = WalletUtils.loadCredentials(password, file.path)
        contract = Contracts_Dragon721_sol_Dragon721.load(
            contractAddress,
            web3,
            credentials,
            RealtimeGasProvider(),
        )

        return contract
    }

    fun loadContract(): Contract {
        val credentials = Credentials.create(
            //"3e7493befe4da8167bf95833109a525358c11f70a8ed9a04239a385a498114a8"
            "ef1162540ed5189c7f7eb5c9bf274767e95aad0559b3920eb3dc50c35aecd465")
        contract = Contracts_Dragon721_sol_Dragon721.load(
            contractAddress,
            web3,
            credentials,
            RealtimeGasProvider(),
        )
        dragon721Contract = contract as Contracts_Dragon721_sol_Dragon721

        return contract
    }

    fun symbol(): String {
        val sb = StringBuilder()

        runBlocking(Dispatchers.IO) {
            dragon721Contract.symbol()
                .flowable()
                .subscribe({
                    sb.append(it)
                }, {
                    sb.append(it.message)
                })
        }

        return sb.toString()
    }

    fun tokenUri(tokenId: Long): String {
        val sb = StringBuilder()

        runBlocking(Dispatchers.IO) {
            dragon721Contract.tokenURI(BigInteger.valueOf(tokenId))
                .flowable()
                .subscribe({
                    sb.append(it)
                }, {
                    sb.append(it.message)
                })
        }

        return sb.toString()
    }

    fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> {
        val artworks = mutableListOf<Contracts_Dragon721_sol_Dragon721.Artwork>()

        runBlocking(Dispatchers.IO) {
            dragon721Contract.allArtworks.flowable()
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

    suspend fun downloadToken(
        tokenId: BigInteger,
        callback: (String) -> Unit,
    ) = coroutineScope {
        dragon721Contract.tokenURI(tokenId)
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
}