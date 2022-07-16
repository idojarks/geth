package com.example.geth

import com.example.geth.data.EtherUrl
import com.example.geth.service.blockchain.Web3Dragon721Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger
import kotlin.io.path.Path

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun downloadToken() {
        var filename = ""

        runBlocking {
            Web3Dragon721Service().run {
                loadContract(
                    contractAddress = EtherUrl.contractAddress,
                    privateKey = "ef1162540ed5189c7f7eb5c9bf274767e95aad0559b3920eb3dc50c35aecd465",
                )

                launch(Dispatchers.IO) {
                    downloadToken(BigInteger.valueOf(1)) { pathname ->
                        filename = Path(pathname).fileName.toString()
                    }
                }
            }
        }

        assertEquals(filename, "eye.png")
    }

    @Test
    fun getGasPrice() {
        RealtimeGasProvider().run {
            getGasPrice(null)
        }
    }

    @Test
    fun allArtworks() {
        Web3Dragon721Service().run {
            loadContract(
                contractAddress = EtherUrl.contractAddress,
                privateKey = "ef1162540ed5189c7f7eb5c9bf274767e95aad0559b3920eb3dc50c35aecd465",
            )

            getAllArtworks().let {
                it.forEach {
                    it.metadataURI
                }
            }
        }
    }
}