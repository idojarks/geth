package com.example.geth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AccountView(navController: NavController) {
    Scaffold(topBar = {
        AppBar(navController)
    }) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "account")
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun AppBar(navController: NavController) {
    TopAppBar(title = {
        Text(text = "Account")
    }, actions = {
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "add account")
        }
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
        }
    })
}

@Preview
@Composable
fun PreviewAccountView() {
    val navController = rememberNavController()
    AccountView(navController)
}