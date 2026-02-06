package com.example.yeahapp.ui.components.profile

/**
 * UI-состояния экрана профиля в зависимости от статуса авторизации пользователя
 */
sealed interface ProfileUiState {
    object Loading : ProfileUiState
    object LoggedOut : ProfileUiState

    data class LoggedIn(
        val firstName: String,
        val lastName: String,
        val login: String,
    ) : ProfileUiState

    data class Error(
        val message: String
    ) : ProfileUiState
}