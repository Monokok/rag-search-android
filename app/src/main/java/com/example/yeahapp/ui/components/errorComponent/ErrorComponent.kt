package com.example.yeahapp.ui.components.errorComponent

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.yeahapp.R

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