package com.example.yeahapp.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.yeahapp.R


/**
 * @param state - состояние профиля пользователя, на основе которого рисуется содержимое карточки профиля
 * @param login - функция, которая будет вызываться при попытке осуществить вход (login)
 * @param logout - функция, которая будет вызываться при попытке осуществить выход] (logout)
 */
@Composable
fun Profile(state: ProfileUIState, login: () -> Unit, logout: () -> Unit): Unit {
//    private val _stateFlow = MutableStateFlow()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        )
    {
        when (state) {
            ProfileUIState.Loading -> {
                LoadingComponent()
            }

            is ProfileUIState.Error -> {
                ErrorComponent(state.message)
            }

            is ProfileUIState.LoggedIn -> {
                LogoutComponent(state, logout)
            }

            ProfileUIState.LoggedOut -> {
                SignInComponent(login)
            }
        }
    }
}

@Composable
fun LogoutComponent(state: ProfileUIState.LoggedIn, logout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(state.login)
        Text(state.firstName)
        Text(state.lastName)
        Button(onClick = logout) {
            Text(stringResource(R.string.logout_title))
        }
    }
}


@Composable
fun LoadingComponent() {
    CircularProgressIndicator()
    Spacer(
        modifier = Modifier.height(
            16.dp
        )
    )

    Text(stringResource(R.string.loading_hint))
}


@Composable
fun ErrorComponent(
    message: String = "",
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.error
) {
    Text(
        text = if (message.isNotEmpty()) "${stringResource(R.string.error_title)}: $message" else stringResource(R.string.error_title),
        modifier,
        color = textColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignInComponent(login: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        val _login = remember { (mutableStateOf("")) }
        var _password = remember { (mutableStateOf("")) }
        Text(stringResource(R.string.sign_in_title))
        TextField(
            placeholder = { Text(stringResource(R.string.sign_in_login_hint)) },
            value = _login.value,
            onValueChange = { newLogin -> _login.value = newLogin },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Person
                    , contentDescription = stringResource(R.string.login_info_hint)
                )
            }
        )
        TextField(
            placeholder = { Text("Введите пароль") },
            value = _password.value,
            onValueChange = { newPassword -> _password.value = newPassword },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                Icon(
                    Icons.Filled.Lock, contentDescription = stringResource(R.string.password_info_hint)
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = login) {
            Text(stringResource(R.string.sign_in_button))
        }
    }
}

//Preview-функции:

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInComponent_Preview() {
    Profile(ProfileUIState.LoggedOut, { Unit }, { Unit })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorComponent_Preview() {
    Profile(ProfileUIState.Error("Нет успеха :("), { Unit }, { Unit })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingComponent_Preview() {
    Profile(ProfileUIState.Loading, { Unit }, { Unit })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutComponent_Preview() {
    Profile(
        state = ProfileUIState.LoggedIn(
            firstName = "Иван",
            lastName = "Иванов",
            login = "ivan@ivan_domain.ru"
        ), { Unit }, { Unit }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Profile_LoggedIn_Preview(): Unit {
    Profile(
        ProfileUIState.LoggedOut, { Unit }, { Unit }
    )
}