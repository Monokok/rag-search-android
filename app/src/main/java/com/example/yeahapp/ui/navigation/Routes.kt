package com.example.yeahapp.ui.navigation

/**
* Описание конечного множества допустимых маршрутов навигации
*
* Обертка над строковыми route,
* чтобы избежать "magic strings" в NavController
*
* @property route строковой ID маршрута,
* используемый в NavHost
* */
sealed class Routes(val route: String) {
    /**
     * Главный экран приложения с чатами
     */
    object Chats: Routes("chats")

    /**
     * Экран профиля пользователя
     */
    object Profile: Routes("profile")

    /**
     * Экран настроек приложения
     */
    object Settings: Routes("settings")

    /**
     * Экран внутри чата
     */
    object Chat: Routes("chat/{chatID}"){
        fun createRoute(chatID: String) = "chat/$chatID"
    }
}



