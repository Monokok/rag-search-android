package com.example.yeahapp.ui.components.profile

/**
 * UI-состояния экрана профиля в зависимости от статуса авторизации пользователя
 */
sealed interface ProfileUIState {
    object Loading : ProfileUIState
    object LoggedOut : ProfileUIState

    data class LoggedIn(
        val firstName: String,
        val lastName: String,
        val login: String,
    ) : ProfileUIState

    data class Error(
        val message: String
    ) : ProfileUIState
}