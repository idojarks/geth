package com.example.geth.ui.screen.home.route.dragon721

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Dragon721ArtworkDetailScreen(
    tokenId: Int,
    title: String,
    artist: String,
) {
    Column {
        Text(text = tokenId.toString())
        Text(text = title)
        Text(text = artist)
    }

}