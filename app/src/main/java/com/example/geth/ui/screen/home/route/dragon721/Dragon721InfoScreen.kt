package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel

@Composable
fun Dragon721InfoScreen() {
    val model = LocalEtherViewModelProvider.current

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Web3",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Version",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = model.dragon721Service.getVersion(),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.padding(14.dp))

        Text(
            text = "Token",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Symbol",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = model.dragon721Service.getSymbol(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun _Dragon721InfoScreen() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
    ) {
        Dragon721InfoScreen()
    }
}
