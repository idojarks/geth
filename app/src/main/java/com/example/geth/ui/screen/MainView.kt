package com.example.geth.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.ui.screen.home.HomeScreen
import com.example.geth.ui.screen.home.route.account.AccountScreen
import com.example.geth.ui.screen.home.route.dragon721.Dragon721ArtworkDetailScreen

val RootNavController = compositionLocalOf<NavHostController> {
    error("")
}

@Composable
fun MainView(
    viewModel: EtherViewModel,
) {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides viewModel,
        RootNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("account") {
                AccountScreen()
            }
            composable(
                route = "artworkDetail?id={id}&title={title}&artist={artist}",
                arguments = listOf(
                    navArgument("id") {
                        defaultValue = -1
                    },
                    navArgument("title") {
                        defaultValue = ""
                    },
                    navArgument("artist") {
                        defaultValue = ""
                    },
                ),
            ) {
                val index = checkNotNull(it.arguments?.getInt("id"))
                val title = checkNotNull(it.arguments?.getString("title"))
                val artist = checkNotNull(it.arguments?.getString("artist"))

                Dragon721ArtworkDetailScreen(
                    index = index,
                    title = title,
                    artist = artist,
                )
            }
        }
    }
}