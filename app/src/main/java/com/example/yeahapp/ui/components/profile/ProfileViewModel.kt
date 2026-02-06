package com.example.yeahapp.ui.components.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * ViewModel Для взаимодействия с профилем пользователя
 */
class ProfileViewModel : ViewModel() {
    var profileState: ProfileUiState by mutableStateOf(ProfileUiState.Loading)
        private set

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            delay(1500) // имитация проверки local storage / token
            val hasSession = Random.nextBoolean()

            profileState = if (hasSession) {
                ProfileUiState.LoggedIn(
                    firstName = "Иван",
                    lastName = "Иванов",
                    login = "YouLoggedInAtLastTime@yeah.you"
                )
            } else {
                ProfileUiState.LoggedOut
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            delay(3500)
            profileState = ProfileUiState.LoggedOut
        }
    }

    fun login(login: String, password: String) {
        try {
            val rnd = Random(0)
            viewModelScope.launch {
                profileState = ProfileUiState.Loading
                delay(5000)
                profileState = if (rnd.nextBoolean()) {
                    ProfileUiState.LoggedIn(
                        firstName = "Иван", lastName = "Иванов", login = login
                    )
                } else {
                    ProfileUiState.Error("котики все сломали :(")
                }

            }
        } catch (e: Exception) {
            ProfileUiState.Error(e.message.toString())
        }
    }
}