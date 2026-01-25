package com.example.yeahapp.ui.components.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yeahapp.ui.components.home.Home
import com.example.yeahapp.ui.components.profile.Profile
import com.example.yeahapp.ui.components.settings.Settings
import com.example.yeahapp.ui.navigation.AppNavBar
import com.example.yeahapp.ui.navigation.Routes

/**
 * Корневой composable-компонент, оборачивающий все приложение
 */
@Composable
fun AppComponent(){
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState() //подписка уровня UI
    val currentRoute = backStackEntry?.destination?.route

    //определяем когда будет происходить показ нижнего навбара
    val showBottomBar = when (currentRoute){
        Routes.Chats.route, Routes.Profile.route -> true
        Routes.Settings.route -> false
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar){
                AppNavBar(navController)
            }
        }
    ) {
        padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Chats.route,
            modifier = Modifier.padding(padding)
        ){
            composable(Routes.Chats.route){ Home() }
            composable(Routes.Settings.route){ Settings() }
            composable(Routes.Profile.route){ Profile() }
            composable(Routes.Chat.route){
                Text("Do not implemented yet. ChatScreen")
            }
        }
    }
}