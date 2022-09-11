package com.example.geth.ui.screen.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenList(
    title: String,
    onClickAddButton: () -> Unit,
    lazyColumnContent: (LazyListScope) -> Unit,
    showProgressIndicator: Boolean,
    isEmpty: Boolean,
) {
    val rootNavController = RootNavController.current

    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            rootNavController.navigate("home")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
                title = {
                    Text(text = title)
                },
                actions = {
                    IconButton(
                        onClick = onClickAddButton,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "add",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            lazyColumnContent(this)
        }

        if (showProgressIndicator) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading")
                }
            }
        }
        else if (isEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("No items")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBasedCard(
    name: String,
    address: String,
    privateKey: String?,
    isDefault: Boolean,
    onClickDefaultButton: () -> Unit,
    onClickEditMenu: () -> Unit,
    onClickDeleteMenu: () -> Unit,
    extraContents: (@Composable () -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                )

                if (isDefault) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "default",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
            )

            Text(
                text = "Address",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                text = address,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(
                modifier = Modifier.padding(4.dp),
            )

            privateKey?.let {
                Text(
                    text = "Private key",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = privateKey,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(
                    modifier = Modifier.padding(4.dp),
                )
            }

            extraContents?.let {
                it()
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }

                if (!isDefault) {
                    OutlinedButton(
                        onClick = onClickDefaultButton,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "default",
                        )
                        Text(
                            text = "Set default",
                            softWrap = false,
                            modifier = Modifier.padding(horizontal = 4.dp),
                        )
                    }
                }

                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopStart),
                ) {
                    IconButton(
                        onClick = {
                            expanded = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "overflow",
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = onClickEditMenu,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "edit",
                                )
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = onClickDeleteMenu,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "delete",
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    val rootNavController = rememberNavController()

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rootNavController,
    ) {
        AddressBasedCard(
            name = "test",
            address = "0xtest",
            privateKey = null,
            isDefault = false,
            onClickDefaultButton = { /*TODO*/ },
            onClickEditMenu = { /*TODO*/ },
            onClickDeleteMenu = {},
        )
    }
}