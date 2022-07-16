package com.example.geth.service.account

import android.content.Context
import android.widget.Toast
import com.example.geth.data.EtherAccount
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class FileAccountRepository(
    private val context: Context,
    private val filename: String,
) : AccountRepository {
    private val accounts = mutableListOf<EtherAccount>()

    override fun getAccounts(): List<EtherAccount> {
        loadIfAccountsEmpty()

        return accounts
    }

    override fun addAccount(account: EtherAccount): List<EtherAccount> {
        account.validateBeforeUse()
        loadIfAccountsEmpty()

        if (accounts.contains(account)) {
            Toast.makeText(context, "account exists", Toast.LENGTH_SHORT)
                .show()

            return accounts
        }

        accounts.add(account)
        updateAccountsToFile()

        return accounts
    }

    override fun deleteAccount(account: EtherAccount): List<EtherAccount> {
        account.validateBeforeUse()
        accounts.remove(account)
        updateAccountsToFile()

        return accounts
    }

    private fun loadIfAccountsEmpty() {
        if (accounts.isNotEmpty()) {
            return
        }

        val file = File(context.filesDir, filename)

        if (!file.exists()) {
            return
        }

        val sb = StringBuilder()

        context.openFileInput(filename)
            .bufferedReader()
            .useLines { lines ->
                lines.forEach {
                    sb.append(it)
                }
            }

        val json = JSONObject(sb.toString())
        val array = json.getJSONArray("accounts")

        if (array.length() == 0) {
            return
        }

        for (i in 0 until array.length()) {
            val account = array.getJSONObject(i)
            EtherAccount(
                name = account.get("name")
                    .toString(),
                address = account.get("address")
                    .toString(),
                privateKey = account.get("pk")
                    .toString(),
                isDefault = account.get("isDefault")
                    .toString()
                    .toBoolean(),
            ).also {
                accounts.add(it)
            }
        }
    }

    private fun updateAccountsToFile() {
        File(context.filesDir, filename).also {
            if (it.exists()) {
                it.delete()
            }
        }

        context.openFileOutput(filename, Context.MODE_PRIVATE)
            .use { outputStream ->
                val array = JSONArray()

                accounts.forEachIndexed { index, etherAccount ->
                    JSONObject().put("name", etherAccount.name)
                        .put("address", etherAccount.address)
                        .put("pk", etherAccount.privateKey)
                        .put("isDefault", etherAccount.isDefault)
                        .also {
                            array.put(index, it)
                        }
                }

                val json = JSONObject().put("accounts", array)

                outputStream.write(json.toString()
                    .toByteArray())
            }
    }
}

fun EtherAccount.validateBeforeUse() {
    if (name.isEmpty()) {
        throw IllegalArgumentException("invalid account: empty Name")
    } else if (address.isEmpty()) {
        throw IllegalArgumentException("invalid account: empty Address")
    } else if (privateKey.isEmpty()) {
        throw IllegalArgumentException("invalid account: empty PrivateKey")
    }
}

