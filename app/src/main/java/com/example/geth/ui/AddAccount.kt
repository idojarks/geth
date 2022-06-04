package com.example.geth.ui

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.geth.SavedAccount
import java.io.File

@Composable
fun AddAccount(
    model: EtherViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (account, setAccount) = remember {
            mutableStateOf("")
        }
        val context = LocalContext.current

        Button(
            onClick = {
                SavedAccount()
                    .save(
                        context,
                        account
                    ) {
                        model.run {
                            val list = mutableListOf<String>()

                            savedAccounts.value?.forEach {
                                list.add(it)
                            }
                            list.add(account)

                            savedAccounts.value = list

                            setAccount("")
                        }
                    }

            }
        ) {
            Text(
                text = "Add"
            )
        }
        TextField(
            value = account,
            onValueChange = {
                setAccount(it)
            }
        )
    }
}

@Preview
@Composable
fun Preview1() {
    AddAccount(
        EtherViewModel()
    )
}