package com.example.yeahapp.ui.components.chat

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeahapp.R
import com.example.yeahapp.ui.theme.LightBlue
import com.example.yeahapp.ui.theme.YeahAppTheme
import kotlin.random.Random


/**
 * Экран чата с запросами и ответами
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatID: String, modifier: Modifier = Modifier) {
    val messageList = getMockForMessages(20)

    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Text("Чат: $chatID",)
            }
        },
        bottomBar = {
            ChatTextFieldComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        },

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(messageList) { msg ->
                MessageItem(msg)
            }
        }

    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, goBack: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        IconButton(
            onClick = goBack
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                    .size(30.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
            )
        }

    }
}


@Composable
fun MessageItem(message: Message) {
    var leftPadding: Dp
    var color: Color
    if (message.type == MessageState.Request) {
        color = LightBlue
        leftPadding = 0.dp
    } else {
        leftPadding = 40.dp
    }

    //определили тип
    val isRequest = message.type == MessageState.Request

    val backgroundColor = if (isRequest) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }

    val textColor = if (isRequest) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    Column(
        modifier = Modifier
            .padding(start = leftPadding + 15.dp, top = 6.dp, bottom = 6.dp, end = 15.dp)
            .background(backgroundColor, shape = RoundedCornerShape(7.dp))

    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Сообщение ${message.id}:",
            fontStyle = FontStyle.Italic,
            color = textColor
        )
        Text(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            text = message.text,
            color = textColor
        )

    }
}

fun getMockForMessages(count: Int): MutableList<Message> {
    val messageList = mutableListOf<Message>()
    val rnd = Random(0)

    (0..count).forEach { index ->
        val msg = Message(
            text = "",
            date = "",
            type = MessageState.Request,
            id = rnd.nextBytes(10).toString()
        )


        //тип сообщения
        if (rnd.nextBoolean()) {

        } else {

        }
        if (index % 2 == 0) {
            msg.id = "Витя спрашивает"
            msg.type = MessageState.Request
            msg.text =
                "Как починить ${rnd.nextBytes(20)}-деталь? У меня сломалось, много сломанного, не едет, как и чем чинить?"
        } else {
            msg.id = "Паша отвечает"
            msg.type = MessageState.Response
            msg.text =
                "Купи: ${rnd.nextBytes(20)}-деталь по артикулу, прикрути, распарсь, сделай, отладь, скомпилируй, пусти в прод, поедь"
        }

        msg.date = "2026-${String.format("%02d", 1 + rnd.nextInt(12))}-${
            String.format(
                "%02d",
                1 + rnd.nextInt(28)
            )
        } " +
                "${String.format("%02d", rnd.nextInt(24))}:${
                    String.format(
                        "%02d",
                        rnd.nextInt(60)
                    )
                }"
        messageList.add(msg)
    }
    return messageList;
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ChatScreenPreview() {
    YeahAppTheme {
        val navController = rememberNavController()
        ChatScreen(navController, "chatID", Modifier.systemBarsPadding())
    }
}


/**
 * Сообщение
 */
data class Message(
    /**
     * Тип сообщения: ответ/запрос
     */
    var id: String, //изменить на val
    var type: MessageState,
    var date: String,
    var text: String,
)

sealed interface MessageState {
    object Response : MessageState //ответ
    object Request : MessageState  //запрос
}
