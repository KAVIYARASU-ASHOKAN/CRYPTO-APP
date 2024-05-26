package com.example.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.CryptoViewModel
import com.example.crypto.model.CoinData
import com.example.crypto.model.Total
import com.example.crypto.model.TotalDao
import com.example.crypto.model.localTransactionData
import com.example.crypto.model.coinDao
import com.iyke.crypto_tracker.screens.AppContent
import com.iyke.crypto_tracker.screens.HomeScreen
import com.iyke.crypto_tracker.screens.Screen
import com.iyke.crypto_tracker.screens.SettingsScreen
import com.iyke.crypto_tracker.screens.Transaction
import com.iyke.crypto_tracker.screens.WalletScreen
import com.iyke.crypto_tracker.ui.theme.CryptoTrackerTheme
import com.iyke.crypto_tracker.ui.theme.Teal200
import com.iyke.crypto_trackersealed.BottomNavItem


class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationGraph( auth = auth)
        }
    }

    @Database(entities = [CoinData::class, localTransactionData::class, Total::class], version = 13)
    abstract class AppDatabase : RoomDatabase() {
        abstract val coinDao : coinDao
        abstract val totalDao : TotalDao
    }
}


@Composable
fun MainScreenView(navController: NavController,auth : FirebaseAuth) {
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        it
        //navController.navigate(BottomNavItem.Home.screen_route)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Wallet,
        BottomNavItem.Trade,
        BottomNavItem.Profile
    )
    NavigationBar(
        containerColor = colorResource(id = R.color.black),
        contentColor = Teal200
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->

            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Teal200,
                    unselectedIconColor = Teal200.copy(alpha = 0.4f),
                    selectedTextColor = Teal200,
                    unselectedTextColor = Teal200.copy(alpha = 0.4f)),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(auth: FirebaseAuth) {

    val cryptromodel : CryptoViewModel = viewModel()

    val navController = rememberNavController()

    NavHost(navController, startDestination = "Intro") {
        composable("Intro"){
            Screen(navController = navController)
        }
        composable("MainScreen"){
            MainScreenView(navController = navController, auth = auth)
        }
        composable(BottomNavItem.Home.screen_route) {
            CryptoTrackerTheme {
                HomeScreen(navController, cryptromodel)
            }
        }
        composable(BottomNavItem.Wallet.screen_route) {
            CryptoTrackerTheme {
                WalletScreen(navController, cryptromodel)
            }

        }
        composable(BottomNavItem.Trade.screen_route) {
            CryptoTrackerTheme {
                Transaction(navController, cryptromodel)
            }
        }
        composable(BottomNavItem.Profile.screen_route) {
            SettingsScreen(navController, cryptromodel)
        }
        composable("signIn") {
            AppContent(auth, navController)
        }

    }
}

object userDetails {
    var user : FirebaseUser? = null
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    //  MainScreenView(auth = auth)
}

