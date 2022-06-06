package com.example.geth

import com.example.geth.http.HttpClient
import com.example.geth.http.Url
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger

class RealtimeGasProvider() : ContractGasProvider {
    override fun getGasPrice(contractFunc: String?): BigInteger {
        val minusOne = BigInteger.valueOf(-1)

        val json = HttpClient.getJson(Url().gasPrice)
            ?: return minusOne

        return runCatching {
            val fee = json.getJSONObject("result")
                .getJSONObject("instant")
                .get("feeCap")
                .toString()

            Convert.toWei(fee, Convert.Unit.GWEI)
                .toBigInteger()
        }.getOrElse {
            ExceptionHandler.onCatchException(it)
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