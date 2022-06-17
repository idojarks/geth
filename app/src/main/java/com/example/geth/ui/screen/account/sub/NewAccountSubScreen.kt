package com.example.geth.ui.screen.account.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.ui.screen.AccountSubScreen

@Composable
fun NewAccountSubScreen(
    navController: NavController,
) {
    val (name, setName) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (address, setAddress) = remember {
        mutableStateOf("")
    }
    val (pk, setPk) = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = AccountSubScreen.NewAccount.resourceId))
            }, actions = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
            })
        },
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    setName(it)
                },
                label = {
                    Text(text = "Name")
                },
                placeholder = {
                    Text(text = "John")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            OutlinedTextField(
                value = address,
                onValueChange = {
                    setAddress(it)
                },
                label = {
                    Text(text = "Address")
                },
                placeholder = {
                    Text(text = "0x0")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                value = pk,
                onValueChange = {
                    setPk(it)
                },
                label = {
                    Text(text = "Private key")
                },
                placeholder = {
                    Text(text = "0x0")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Button(onClick = {

            }, modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)) {
                Text(text = stringResource(id = R.string.nav_registerAccount))
            }
        }
    }
}

@Preview
@Composable
fun PreviewNewAccount() {
    val navController = rememberNavController()
    NewAccountSubScreen(navController = navController)
}

