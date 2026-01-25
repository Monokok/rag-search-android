package com.example.yeahapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavBar(navController: NavController){
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState() //
        val currentRoute = backStackEntry?.destination?.route //идентифицировать текущий маршрут

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
//                modifier = Modifier.clipToBounds(),
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppNavBarPreview() {
    val navController = rememberNavController()

    AppNavBar(navController = navController)
}



object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Главная",
            image = Icons.Filled.Home,
            route = "home"
        ),
        BarItem(
            title = "Профиль",
            image = Icons.Filled.AccountCircle,
            route = "profile"
        ),
        BarItem(
            title = "Настройки",
            image = Icons.Filled.Settings,
            route = "settings"
        )
    )
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)