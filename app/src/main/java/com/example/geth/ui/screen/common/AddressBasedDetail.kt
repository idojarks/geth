package com.example.geth.ui.screen.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import com.example.geth.web3.Web3Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBasedDetail(
    title: String,
    onClickBackButton: () -> Unit,
    name: String,
    onChangeName: (String) -> Unit,
    address: String,
    onChangeAddress: (String) -> Unit,
    privateKey: String?,
    onChangePrivateKey: ((String) -> Unit)?,
    isDefault: Boolean,
    onCheckedChangeDefault: (Boolean) -> Unit,
    onDone: () -> Unit,
    doneButtonName: String,
) {
    val (isErrorName, setErrorName) = remember {
        mutableStateOf(false)
    }
    val (errorTextName, setErrorTextName) = remember {
        mutableStateOf("")
    }

    val (isErrorAddress, setErrorAddress) = remember {
        mutableStateOf(false)
    }
    val (errorTextAddress, setErrorTextAddress) = remember {
        mutableStateOf("")
    }

    val (isErrorPrivateKey, setErrorPrivateKey) = remember {
        mutableStateOf(false)
    }
    val (errorTextPrivateKey, setErrorTextPrivateKey) = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = title,
                    )
                },
                navigationIcon = {
                    // back
                    IconButton(
                        onClick = onClickBackButton,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    onChangeName(it)
                    setErrorName(false)
                    setErrorTextName("")
                },
                label = {
                    Text(text = "Name")
                },
                placeholder = {
                    Text(text = "2 ~ 20 characters")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = isErrorName,
            )
            Text(
                text = errorTextName,
                modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                color = Color.Red,
            )

            OutlinedTextField(
                value = address,
                onValueChange = {
                    onChangeAddress(it)
                    setErrorAddress(false)
                    setErrorTextAddress("")
                },
                label = {
                    Text(text = "Address")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isErrorAddress,
            )
            Text(
                text = errorTextAddress,
                modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                color = Color.Red,
            )

            privateKey?.let { text ->
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        if (onChangePrivateKey != null) {
                            onChangePrivateKey(it)
                        }
                        setErrorPrivateKey(false)
                        setErrorTextPrivateKey("")
                    },
                    label = {
                        Text(text = "Private key")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isErrorPrivateKey,
                )
                Text(
                    text = errorTextPrivateKey,
                    modifier = Modifier.padding(PaddingValues(start = 8.dp, bottom = 10.dp)),
                    color = Color.Red,
                )
            }

            Row {
                Checkbox(
                    checked = isDefault,
                    onCheckedChange = onCheckedChangeDefault,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                Text(
                    text = "Default",
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically),
                )
            }

            Button(
                onClick = fun() {
                    if (name.isEmpty()) {
                        setErrorName(true)
                        setErrorTextName("empty name")
                        return
                    }

                    if (name.length !in 2..20) {
                        setErrorName(true)
                        setErrorTextName("Invalid name length")
                        return
                    }

                    if (address.isEmpty()) {
                        setErrorAddress(true)
                        setErrorTextAddress("empty address")
                        return
                    }

                    if (!Web3Utils.isAddress(address = address)) {
                        setErrorAddress(true)
                        setErrorTextAddress("invalid address")
                        return
                    }

                    privateKey?.let {
                        if (it.isEmpty()) {
                            setErrorPrivateKey(true)
                            setErrorTextPrivateKey("empty private key")
                            return
                        }

                        if (!Web3Utils.isPrivateKey(pk = it)) {
                            setErrorPrivateKey(true)
                            setErrorTextPrivateKey("invalid private key")
                            return
                        }
                    }

                    onDone()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
            ) {
                Text(text = doneButtonName)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val rootNavController = rememberNavController()

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rootNavController,
    ) {
        AddressBasedDetail(
            title = "title",
            onClickBackButton = { /*TODO*/ },
            name = "test",
            onChangeName = {},
            address = "0xaddress",
            onChangeAddress = {},
            privateKey = "0xprivate",
            onChangePrivateKey = {},
            isDefault = false,
            onCheckedChangeDefault = {},
            onDone = { /*TODO*/ },
            doneButtonName = "done",
        )
    }
}