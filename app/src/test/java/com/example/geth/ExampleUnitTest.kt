package com.example.geth

import com.example.geth.service.blockchain.Dragon721ContractService
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    /*
    @Test
    fun downloadToken() {
        var filename = ""

        runBlocking {
            Dragon721ContractService().run {
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

     */

    @Test
    fun getGasPrice() {
        RealtimeGasProvider().run {
            getGasPrice(null)
        }
    }

    @Test
    fun allArtworks() {
        Dragon721ContractService().run {
            load(
                contractAddress = "",
                accountPrivateKey = "",
            )

            artworks.let {
                it.forEach {
                    it.metadataURI
                }
            }
        }
    }
}