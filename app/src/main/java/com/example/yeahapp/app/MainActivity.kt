//определяем пакет к которому принадлежит класс
package com.example.yeahapp.app

//подключаем необходимые пакеты
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yeahapp.ui.navigation.AppNavBar
import com.example.yeahapp.ui.navigation.Routes
import com.example.yeahapp.ui.components.home.Home
import com.example.yeahapp.ui.components.settings.Settings
import com.example.yeahapp.ui.components.profile.Profile
//важный импорт
import com.example.yeahapp.ui.theme.YeahAppTheme
import com.example.yeahapp.ui.components.app.AppComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //верхние и нижние (правые и левые) панели устройства прозрачными.
        enableEdgeToEdge()

        setContent {
            YeahAppTheme {
                AppComponent()
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    YeahAppTheme {
        AppComponent()
    }
}
