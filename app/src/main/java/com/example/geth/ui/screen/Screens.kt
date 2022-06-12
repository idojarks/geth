package com.example.geth.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
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
    object Info : Screen("info", R.string.nav_info, Icons.Filled.Info, "info")
}