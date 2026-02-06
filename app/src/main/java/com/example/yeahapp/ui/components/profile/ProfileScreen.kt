package com.example.yeahapp.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yeahapp.R
import com.example.yeahapp.ui.components.errorComponent.ErrorComponent
import com.example.yeahapp.ui.components.loadingComponent.LoadingComponent


/**
 * @param state - состояние профиля пользователя, на основе которого рисуется содержимое карточки профиля
 * @param login - функция, которая будет вызываться при попытке осуществить вход (login)
 * @param logout - функция, которая будет вызываться при попытке осуществить выход] (logout)
 */
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {

    val state = (viewModel.profileState)

    ProfileComponent(state, { login, password ->
        viewModel.login(
            login,
            password
        )
    }, { viewModel.logout() })
}

@Composable
fun ProfileComponent(
    state: ProfileUiState, onLogin: (login: String, password: String) -> Unit, onLogout: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        when (state) {
            ProfileUiState.Loading -> {
                LoadingComponent()
            }

            is ProfileUiState.Error -> {
                ErrorComponent(state.message)
            }

            is ProfileUiState.LoggedIn -> {
                LogoutComponent(state, onLogout)
            }

            ProfileUiState.LoggedOut -> {
                SignInComponent(onLogin)
            }
        }
    }
}

@Composable
fun LogoutComponent(state: ProfileUiState.LoggedIn, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(state.login)
        Text(state.firstName)
        Text(state.lastName)
        Button(onClick = onLogout) {
            Text(stringResource(R.string.logout_title))
        }
    }
}


@Composable
fun SignInComponent(onLogin: (login: String, password: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val _login = remember { (mutableStateOf("")) }
        val _password = remember { (mutableStateOf("")) }
        Text(stringResource(R.string.sign_in_title))
        TextField(
            placeholder = { Text(stringResource(R.string.sign_in_login_hint)) },
            value = _login.value,
            onValueChange = { newLogin -> _login.value = newLogin },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(R.string.login_info_hint)
                )
            })
        TextField(
            placeholder = { Text("Введите пароль") },
            value = _password.value,
            onValueChange = { newPassword -> _password.value = newPassword },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = stringResource(R.string.password_info_hint)
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { onLogin(_login.value, _password.value) }) {
            Text(stringResource(R.string.sign_in_button))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorComponent_Preview() {
    ProfileComponent(
        ProfileUiState.Error("Мок-ошибка для Preview"),
        { login, password -> println("Preview login click: $login / $password") },
        {})
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingComponent_Preview() {
    ProfileComponent(ProfileUiState.Loading, { _, _ -> }, {})
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutComponent_Preview() {
    ProfileComponent(ProfileUiState.LoggedOut, { _, _ -> }, {
        println("Preview logout click!")
    })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Profile_Screen_LoggedIn_Preview() {
    ProfileComponent(
        ProfileUiState.LoggedIn(
            firstName = "Иван", lastName = "Иванов", login = "ivan@yeah.you"
        ), { login, password -> println("Preview login click: $login / $password") }, {
            println("Preview logout click!")
        })
}