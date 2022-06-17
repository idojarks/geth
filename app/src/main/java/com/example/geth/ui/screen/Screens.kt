package com.example.geth.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.geth.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val description: String,
) {
    object Home : Screen("home", R.string.nav_home, Icons.Filled.Home, "home")
    object Account : Screen("account", R.string.nav_account, Icons.Filled.AccountCircle, "account")
    object Settings : Screen("settings", R.string.nav_settings, Icons.Filled.Settings, "settings")
}

sealed class HomeSubScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val description: String,
) {
    object Info : HomeSubScreen("info", R.string.nav_info, Icons.Filled.Info, "info")
}

sealed class AccountSubScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val description: String,
) {
    object AccountList : AccountSubScreen("accountList", R.string.nav_accountList, Icons.Filled.List, "accounts")
    object NewAccount : AccountSubScreen("newAccount", R.string.nav_newAccount, Icons.Filled.Face, "new account")
}