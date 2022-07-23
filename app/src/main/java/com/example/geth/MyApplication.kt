package com.example.geth

import android.app.Application
import android.content.Context
import com.example.geth.data.EtherViewModel
import com.example.geth.service.account.AccountRepository
import com.example.geth.service.account.FileAccountRepository
import com.example.geth.service.blockchain.Dragon721Service
import com.example.geth.service.blockchain.Web3Dragon721Service
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

            single<Dragon721Service> {
                Web3Dragon721Service()
            }

            viewModel {
                EtherViewModel(get(), get())
            }
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modelModule)
        }
    }
}
