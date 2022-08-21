package com.example.geth.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
    val description: String,
) {
    @Composable
    abstract fun getFilledIcon(): ImageVector

    @Composable
    abstract fun getOutlinedIcon(): ImageVector

    object Dragon721Info : HomeSubScreen("dragon721Info", R.string.nav_info, "info") {
        @Composable
        override fun getFilledIcon() = Icons.Filled.Info

        @Composable
        override fun getOutlinedIcon() = Icons.Outlined.Info
    }

    object Dragon721Tokens : HomeSubScreen("dragon721Tokens", R.string.nav_dragon721Tokens, "tokens") {
        @Composable
        override fun getFilledIcon() = ImageVector.vectorResource(id = R.drawable.ic_list_alt_fill1_wght400_grad0_opsz24)

        @Composable
        override fun getOutlinedIcon() = ImageVector.vectorResource(id = R.drawable.ic_list_alt_fill0_wght400_grad0_opsz24)
    }

    object ContractSettings : HomeSubScreen("contractSettings", R.string.nav_contract_settings, "contract") {
        @Composable
        override fun getFilledIcon() = Icons.Filled.Email

        @Composable
        override fun getOutlinedIcon() = Icons.Outlined.Email
    }
}

sealed class AccountSubScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val description: String,
) {
    object All : AccountSubScreen(route = "all", R.string.nav_all_accounts, "all")
    object New : AccountSubScreen("new", R.string.newAccount, "new")
}