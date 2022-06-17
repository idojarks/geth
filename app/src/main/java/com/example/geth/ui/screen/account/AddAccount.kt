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
import com.example.geth.SavedAccount
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModelInterface

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
