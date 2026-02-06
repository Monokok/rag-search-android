package com.example.yeahapp.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yeahapp.R

@Composable
fun ChatTextFieldComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(50.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .weight(1f),
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            placeholder = { Text(stringResource(R.string.enter_a_message)) },
            trailingIcon = {
                IconButton(onClick = {
                    /*send*/
                }, content = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.login_info_hint)
                    )
                })
            },
            singleLine = true,
        )


    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ChatTextFieldComponent_Preview() {
    MaterialTheme() {
        Scaffold(
            bottomBar = { ChatTextFieldComponent() }
        ) {

        }

    }

}