package com.example.geth.ui.screen.account

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.geth.EtherAccount
import com.example.geth.EtherViewModel
import com.example.geth.EtherViewModelInterface
import com.example.geth.SavedAccount

@Composable
fun AddAccount(model: EtherViewModelInterface) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val (account, setAccount) = remember {
            mutableStateOf("")
        }
        val context = LocalContext.current

        Button(onClick = {
            SavedAccount().save(context, account) {
                model.addAccount {
                    EtherAccount(
                        name = "john",
                        address = account,
                        privateKey = "",
                    )
                }
                setAccount("")
            }
        }) {
            Text(text = "Add")
        }
        TextField(value = account, onValueChange = {
            setAccount(it)
        })
    }
}

@Preview
@Composable
fun Preview1() {
    AddAccount(EtherViewModel())
}