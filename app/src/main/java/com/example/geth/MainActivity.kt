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
import com.example.geth.ui.HomeView
import com.example.geth.ui.theme.GethTheme

class MainActivity : ComponentActivity() {
    private val model: EtherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GethTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    HomeView(
                        buildModelLiveData = model.buildModel,
                        web3ClientVersionLiveData = model.web3ClientVersion,
                    )
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



