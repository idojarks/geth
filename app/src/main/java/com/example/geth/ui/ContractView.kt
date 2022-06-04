package com.example.geth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.geth.Ether
import java.math.BigInteger

@Composable
fun Contract(
    ether: Ether
) {
    //val context = LocalContext.current
     val symbol =
        remember {
            ether.symbol(
            )
        }
    val tokenUri =
        remember {
            ether.tokenUri(
                1
            )
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                8.dp
            )
    ) {
        Text(
            modifier = Modifier.padding(
                4.dp
            ),
            text = "symbol: $symbol",
        )
        Text(
            modifier = Modifier.padding(
                4.dp
            ),
            text = "tokenURI: $tokenUri"
        )
    }
}