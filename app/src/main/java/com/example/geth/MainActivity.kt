package com.example.geth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geth.data.AccountRepository
import com.example.geth.data.EtherViewModel
import com.example.geth.ui.screen.MainView
import com.example.geth.ui.theme.GethTheme

class MainActivity : ComponentActivity() {
    private val model: EtherViewModel by viewModels {
        EtherViewModelFactory(
            accountRepository = AccountRepository(
                context = applicationContext,
                filename = "accountTable",
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GethTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MainView(modelInterface = model)
                }
            }
        }

        /*
        lifecycleScope.launchWhenStarted {
            Ether().run {
                init(model)
            }
        }

         */
    }
}

class EtherViewModelFactory(
    val accountRepository: AccountRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EtherViewModel::class.java)) {
            return EtherViewModel(
                accountRepository = accountRepository,
            ) as T
        }
        throw IllegalArgumentException("invalid view model class.")
    }
}

