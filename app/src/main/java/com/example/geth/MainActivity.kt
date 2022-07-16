package com.example.geth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.ui.screen.MainView
import com.example.geth.ui.theme.GethTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    /*
    private val model: EtherViewModel by viewModels {
        EtherViewModelFactory(accountRepository = FileAccountRepository(
            context = applicationContext,
            filename = "accountTable",
        ), dragon721Service = Web3Dragon721Service())
    }

     */

    private val model: EtherViewModel by viewModel()

    @OptIn(ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GethTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val handler = CoroutineExceptionHandler { _, throwable ->
                        // process the Throwable
                        Log.e("eth", "There has been an issue: ", throwable)
                    }

                    CompositionLocalProvider(
                        LocalEtherViewModelProvider provides model,
                        LocalViewModelStoreOwner provides viewModelStoreOwner,
                        LocalFontFamilyResolver provides createFontFamilyResolver(LocalContext.current, handler),
                    ) {
                        MainView()
                    }
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


/*
class EtherViewModelFactory(
    val accountRepository: AccountRepository,
    val dragon721Service: Dragon721Service,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EtherViewModel::class.java)) {
            return EtherViewModel(
                accountRepository = accountRepository,
                dragon721Service = dragon721Service,
            ) as T
        }
        throw IllegalArgumentException("invalid view model class.")
    }
}

 */

