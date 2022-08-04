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
    private var accounts = listOf<EtherAccount>()

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

        val list = mutableListOf<EtherAccount>()

        if (account.isDefault) {
            list.add(account)
            list.addAll(accounts.map {
                it.isDefault = false
                it
            })
        } else {
            list.addAll(accounts)
            list.add(account)
        }

        accounts = list.sortedByDescending {
            it.isDefault
        }
        updateAccountsToFile()

        return accounts
    }

    override fun deleteAccount(account: EtherAccount): List<EtherAccount> {
        account.validateBeforeUse()

        val list = accounts.filter {
            it != account
        }

        if (list.isNotEmpty() && account.isDefault) {
            list.first()
                .apply {
                    isDefault = true
                }
        }

        accounts = list
        updateAccountsToFile()

        return accounts
    }

    override fun setDefaultAccount(account: EtherAccount): List<EtherAccount> {
        if (account.isDefault) {
            return accounts
        }

        account.validateBeforeUse()

        accounts = accounts.map {
                it.isDefault = it == account
                it
            }
            .sortedByDescending {
                it.isDefault
            }

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

        if (sb.isEmpty()) {
            return
        }

        val json = JSONObject(sb.toString())
        val array = json.getJSONArray("accounts")

        if (array.length() == 0) {
            return
        }

        val list = mutableListOf<EtherAccount>()

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
                list.add(it)
            }
        }

        accounts = list
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

