package com.example.yeahapp.ui.components.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeahapp.R
import com.example.yeahapp.ui.components.errorComponent.ErrorComponent
import com.example.yeahapp.ui.components.loadingComponent.LoadingComponent
import com.example.yeahapp.ui.navigation.AppNavBar
import com.example.yeahapp.ui.navigation.Routes
import com.example.yeahapp.ui.theme.YeahAppTheme
import kotlin.random.Random

//@Suppress("USELESS_IS_CHECK")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Chats(
    navController: NavController
) {
    //тут вызов VM и получение списка чатов
    //создание ChatsUIState на основе содержимого
    //далее логика обработки state
    val itemList: MutableList<ChatItem> = mutableListOf()
    val rnd = Random(0)
    (0..10).forEach { index ->
        itemList.add(
            ChatItem(
                header = "Чат №$index", date = "2025-10-15 15:50", id = rnd.nextBits(10).toString()
            )
        )
    }


    val state = ChatsUIState.Content(
        chats = itemList
    )//MutableStateFlow<ChatsUIState>(ChatsUIState.Loading)



    Scaffold(
        bottomBar = { AppNavBar(navController) }, modifier = Modifier.systemBarsPadding()
    ) { padding ->
        when (state) {
            is ChatsUIState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) { LoadingComponent() }
            }

            is ChatsUIState.Content -> {
                ChatListComponent(
                    state.chats, // обратите внимание: здесь state уже smart-cast
                    modifier = Modifier.padding(padding), onClick = { chatID ->
                        navController.navigate(Routes.Chat.createRoute(chatID))
                    })
            }

            is ChatsUIState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) { ErrorComponent() }
            }
        }
    }
}


@Composable
fun ChatListComponent(
    itemList: MutableList<ChatItem> = mutableListOf(),
    onClick: (chatID: String) -> Unit,
    modifier: Modifier = Modifier
) {

    if (itemList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(15.dp), text = stringResource(R.string.no_chats)
            )
            Button(
                modifier = Modifier.padding(2.dp), onClick = {
                    print("RefreshButton click!")
                }) {
                Text(
                    text = stringResource(R.string.retry_request),
                )
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.retry_request)
                )
            }

        }

    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = itemList,

                ) { item ->
                ChatItemRow(
                    item = item, onClick
                )
            }
        }
    }
}

@Composable
fun ChatItemRow(item: ChatItem, onClick: (chatID: String) -> Unit) {
    Row(
        modifier = Modifier
//            .background(color = MaterialTheme.colorScheme.)
            .fillMaxWidth()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {
                onClick(item.id)
            }) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .size(48.dp)
                .background(color = MaterialTheme.colorScheme.onTertiary, CircleShape)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.MailOutline,
                contentDescription = stringResource(R.string.chat_title, item.id)
            )
        }
        Column(
            modifier = Modifier.padding(start = 10.dp)

        ) {
            Text(
                text = item.header,

                )
            Text(
                text = item.date
            )
            Text(
                text = "ID: ${item.id.take(6)}"
            )
        }
        Spacer(modifier = Modifier.weight((1f)))
        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)
                .align(alignment = Alignment.CenterVertically),

            imageVector = Icons.Filled.PlayArrow, contentDescription = stringResource(
                R.string.move_to_chat, item.id
            )
        )
    }
}


@Composable
@Preview(showSystemUi = false, showBackground = true)
fun ChatListComponent_Preview() {


    val itemList: MutableList<ChatItem> = mutableListOf()
    val rnd = Random(0)
    (0..10).forEach { index ->
        itemList.add(
            ChatItem(
                header = "Чат №$index", date = "2025-10-15 15:50", id = rnd.nextBits(10).toString()
            )
        )
    }

    YeahAppTheme(

    ) {
        ChatListComponent(itemList, { print("Click!") }, modifier = Modifier.systemBarsPadding())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EmptyChatListComponent_Preview() {
    ChatListComponent(mutableListOf(), {})
}


@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun Chats_Preview() {
    YeahAppTheme() {
        Surface(

        ) {
            val navMock = rememberNavController()
            Chats(navMock)
        }

    }

}

