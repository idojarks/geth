package com.example.geth.ui.screen.account.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.ui.screen.AccountSubScreen

@Composable
fun AccountListSubScreen(
    parentNavController: NavController,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = AccountSubScreen.AccountList.resourceId),
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(AccountSubScreen.NewAccount.route)
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "new account")
                    }
                    IconButton(
                        onClick = {
                            parentNavController.popBackStack()
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Preview
@Composable
fun PreviewAccountList() {
    AccountListSubScreen(
        parentNavController = rememberNavController(),
        navController = rememberNavController(),
    )
}
