package com.example.geth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
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
            Ether().run {
                init("https://ropsten.infura.io/v3/c7c3743a100841048f439743d078ea0d")
                loadContract()

                launch(Dispatchers.IO) {
                    downloadToken(BigInteger.valueOf(1)) { pathname ->
                        filename = Path(pathname).fileName.toString()
                    }
                }
            }
        }

        assertEquals(filename, "output.png")
    }

    @Test
    fun getGasPrice() {
        RealtimeGasProvider().run {
                val price = getGasPrice(null)
                println(price)
            }
    }

    @Test
    fun allArtworks() {
        Ether().run {
            init("https://ropsten.infura.io/v3/c7c3743a100841048f439743d078ea0d")
            loadContract()
            getAllArtworks().let {
                it.forEach {
                    it.metadataURI
                }
            }
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}