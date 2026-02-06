package com.example.yeahapp.ui.components.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yeahapp.ui.components.chat.ChatScreen
import com.example.yeahapp.ui.components.home.Chats
import com.example.yeahapp.ui.components.profile.ProfileScreen
import com.example.yeahapp.ui.components.profile.ProfileViewModel
import com.example.yeahapp.ui.components.settings.Settings
import com.example.yeahapp.ui.navigation.Routes

/**
 * Корневой composable-компонент, оборачивающий все приложение
 */
@Composable
fun AppComponent() {
    val navController = rememberNavController()

//    val backStackEntry by navController.currentBackStackEntryAsState() //подписка уровня UI
//    val currentRoute = backStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Routes.Chats.route,
//            modifier = Modifier.padding(padding)
    ) {
        composable(Routes.Chats.route) { Chats(navController) }
        composable(Routes.Settings.route) { Settings() }
        composable(Routes.Profile.route) {
            ProfileScreen(
                ProfileViewModel()
            )
            composable(
                Routes.Chat.route,
                arguments = listOf(
                    navArgument("chatID") {
                        type = NavType.StringType
                    }
                )

            ) { backStackEntry ->
                val chatID = backStackEntry.arguments?.getString("chatID")
                if (chatID != null) {
                    ChatScreen(navController, chatID)
                } else {
                    Text("Chat ID not found")
                }
            }
        }
    }
}