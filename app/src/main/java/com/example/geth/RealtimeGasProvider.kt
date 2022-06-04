package com.example.geth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.web3j.protocol.Web3j
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger

class RealtimeGasProvider() :
    ContractGasProvider {
    override fun getGasPrice(contractFunc: String?): BigInteger {
        val minusOne = BigInteger.valueOf(-1)

        val json = HttpClient.getJson("https://api.gasprice.io/v1/estimates")
            ?: return minusOne

        return try {
            val fee = json.getJSONObject("result")
                .getJSONObject("instant")
                .get("feeCap")
                .toString()

            Convert.toWei(fee, Convert.Unit.GWEI)
                .toBigInteger()
        } catch (e: Exception) {
            minusOne
        }
    }

    @Deprecated(
        "Deprecated in Java",
        replaceWith = ReplaceWith("getGasPrice(null)"),
    )
    override fun getGasPrice(): BigInteger {
        return getGasPrice(null)
    }

    override fun getGasLimit(contractFunc: String?): BigInteger {
        return DefaultGasProvider.GAS_LIMIT
    }

    @Deprecated(
        "Deprecated in Java",
        replaceWith = ReplaceWith("getGasLimit(null)"),
    )
    override fun getGasLimit(): BigInteger {
        return getGasLimit(null)
    }

}