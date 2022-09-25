package com.example.geth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import com.example.geth.data.EtherViewModel
import com.example.geth.ui.screen.MainView
import com.example.geth.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val model: EtherViewModel<Contracts_Dragon721_sol_Dragon721, Artwork> by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainView(
                        viewModel = model,
                    )
                }
            }
        }
    }
}



