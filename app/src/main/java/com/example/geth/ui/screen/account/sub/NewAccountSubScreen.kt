package com.example.geth.ui.screen.account.sub

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.account.EtherAccount
import com.example.geth.ui.screen.AccountSubScreen
import com.example.geth.web3.Web3Utils

@Composable
fun NewAccountSubScreen(
    navController: NavController,
) {
    val model = LocalEtherViewModelProvider.current

    // name
    val (name, setName) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (nameError, setNameError) = remember {
        mutableStateOf(false)
    }
    val (nameErrorText, setNameErrorText) = remember {
        mutableStateOf("")
    }

    // address
    val (address, setAddress) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (addressError, setAddressError) = remember {
        mutableStateOf(false)
    }
    val (addressErrorText, setAddressErrorText) = remember {
        mutableStateOf("")
    }

    // private key
    val (pk, setPk) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (pkError, setPkError) = remember {
        mutableStateOf(false)
    }
    val (pkErrorText, setPkErrorText) = remember {
        mutableStateOf("")
    }

    val (defaultAccount, setDefaultAccount) = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = AccountSubScreen.NewAccount.resourceId))
            }, actions = {
                // back
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
                    setNameError(false)
                    setNameErrorText("")
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
                isError = nameError,
            )
            Text(
                text = nameErrorText,
                modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                color = Color.Red,
            )
            OutlinedTextField(
                value = address,
                onValueChange = {
                    setAddress(it)
                    setAddressError(false)
                    setAddressErrorText("")
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
                isError = addressError,
            )
            Text(
                text = addressErrorText,
                modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                color = Color.Red,
            )
            OutlinedTextField(
                value = pk,
                onValueChange = {
                    setPk(it)
                    setPkError(false)
                    setPkErrorText("")
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
                isError = pkError,
            )
            Text(
                text = pkErrorText,
                modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                color = Color.Red,
            )
            Row {
                Checkbox(checked = defaultAccount, onCheckedChange = {
                    setDefaultAccount(it)
                }, modifier = Modifier.align(Alignment.CenterVertically))
                Text(text = "default account", modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically), fontSize = 16.sp)
            }
            // register
            Button(
                onClick = {
                    if (name.text.isEmpty()) {
                        setNameError(true)
                        setNameErrorText("empty name")
                        return@Button
                    }

                    if (address.text.isEmpty()) {
                        setAddressError(true)
                        setAddressErrorText("empty address")
                        return@Button
                    }

                    if (!Web3Utils.isAddress(address = address.text)) {
                        setAddressError(true)
                        setAddressErrorText("invalid address")
                        return@Button
                    }

                    if (pk.text.isEmpty()) {
                        setPkError(true)
                        setPkErrorText("empty private key")
                        return@Button
                    }

                    if (!Web3Utils.isPk(pk = pk.text)) {
                        setPkError(true)
                        setPkErrorText("invalid private key")
                        return@Button
                    }

                    model.addAccount {
                        EtherAccount(name = name.text, address = address.text, privateKey = pk.text, isDefault = defaultAccount)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
            ) {
                Text(text = stringResource(id = R.string.nav_registerAccount))
            }
        }
    }
}

@Preview
@Composable
fun PreviewNewAccount() {
    val navController = rememberNavController()
    NewAccountSubScreen(
        navController = navController,
    )
}

