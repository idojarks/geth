package com.example.geth

import com.example.geth.http.Url
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
            Ether().run {
                init(Url().infuraRopsten)
                loadContract()

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
        Ether().run {
            init(Url().infuraRopsten)
            loadContract()
            getAllArtworks().let {
                it.forEach {
                    it.metadataURI
                }
            }
        }
    }
}