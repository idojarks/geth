package com.example.geth

import android.app.Application
import android.content.Context
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import com.example.geth.data.EtherViewModel
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.FileAccountRepository
import com.example.geth.service.blockchain.Dragon721ContractService
import com.example.geth.service.blockchain.Web3ContractService
import com.example.geth.service.contract.ContractFileRepository
import com.example.geth.service.contract.ContractRepository
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication

        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        val modelModule = module {
            single<AccountRepository> {
                FileAccountRepository(
                    context = getContext(),
                    filename = "accountTable",
                )
            }

            single<ContractRepository> {
                ContractFileRepository(
                    context = getContext(),
                    filename = "contracts",
                )
            }

            single<Web3ContractService<Contracts_Dragon721_sol_Dragon721, Artwork>> {
                Dragon721ContractService()
            }

            viewModel {
                EtherViewModel(get(), get(), get<Web3ContractService<Contracts_Dragon721_sol_Dragon721, Artwork>>())
            }
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modelModule)
        }
    }
}
