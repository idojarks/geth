package com.example.geth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.geth.ui.EtherViewModel
import com.example.geth.ui.screen.MainView
import com.example.geth.ui.theme.GethTheme

class MainActivity : ComponentActivity() {
    private val model: EtherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LiveDataContainer.buildModelLiveData = model.buildModel
        LiveDataContainer.web3ClientVersionLiveData = model.web3ClientVersion

        setContent {
            GethTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MainView()
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



