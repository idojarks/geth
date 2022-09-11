package com.example.geth.service.contract

import android.content.Context
import android.widget.Toast
import com.example.geth.data.EtherContract
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ContractFileRepository(
    private val context: Context,
    private val filename: String,
    private val showToast: Boolean = true,
) : ContractRepository {
    override val contracts by lazy {
        load()
    }

    override fun add(contract: EtherContract): Boolean {
        contracts.find {
            it.address == contract.address
        }
            ?.let {
                if (showToast) {
                    Toast.makeText(context, "Same address found.", Toast.LENGTH_SHORT)
                        .show()
                }

                return false
            }

        contracts.add(contract)
        return save()
    }

    override fun delete(contract: EtherContract): Boolean {
        contracts.find {
            it.address == contract.address
        }
            ?: return false

        contracts.removeIf {
            it.address == contract.address
        }

        return save()
    }

    override fun replace(old: EtherContract, new: EtherContract, save: Boolean): Boolean {
        val index = contracts.indexOfFirst {
            it.address == old.address
        }

        if (index == -1) {
            return false
        }

        contracts.removeAt(index)
        contracts.add(index, new)

        return save()
    }

    override fun setDefault(contract: EtherContract): Boolean {
        val old = contracts.find {
            it.address == contract.address
        }
            ?: return false

        contracts.find {
            it.isDefault
        }
            ?.let {
                val new = EtherContract(name = it.name, address = it.address, isDefault = false)

                if (!replace(it, new, false)) {
                    return false
                }
            }

        val new = EtherContract(
            name = contract.name,
            address = contract.address,
            isDefault = true,
        )

        return replace(old, new)
    }

    override fun getDefault(): EtherContract? {
        return contracts.find {
            it.isDefault
        }
    }

    override fun get(address: String): EtherContract? {
        return contracts.find {
            it.address == address
        }
    }

    fun deleteAll() {
        contracts.clear()

        with(File(context.filesDir, filename)) {
            if (exists()) {
                delete()
            }
        }
    }

    fun reload() {
        contracts.clear()
        val loadedContracts = load()
        contracts.addAll(loadedContracts)
    }

    private fun save(): Boolean {
        with(File(context.filesDir, filename)) {
            if (exists()) {
                delete()
            }
        }

        context.openFileOutput(filename, Context.MODE_PRIVATE)
            .use { outputStream ->
                val array = JSONArray()

                contracts.forEachIndexed { index, contract ->
                    with(JSONObject().put("name", contract.name)
                        .put("address", contract.address)
                        .put("isDefault", contract.isDefault)) {
                        array.put(index, this)
                    }
                }

                val json = JSONObject().put("contracts", array)

                outputStream.write(json.toString()
                    .toByteArray())
            }

        return true
    }

    private fun load(): MutableList<EtherContract> {
        val file = File(context.filesDir, filename)

        if (!file.exists()) {
            return mutableListOf()
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
            return mutableListOf()
        }

        val json = JSONObject(sb.toString())
        val array = json.getJSONArray("contracts")

        if (array.length() == 0) {
            return mutableListOf()
        }

        val list = mutableListOf<EtherContract>()

        for (i in 0 until array.length()) {
            val contract = array.getJSONObject(i)

            EtherContract(
                name = contract.get("name")
                    .toString(),
                address = contract.get("address")
                    .toString(),
                isDefault = contract.get("isDefault")
                    .toString()
                    .toBoolean(),
            ).also {
                list.add(it)
            }
        }

        return list
    }
}