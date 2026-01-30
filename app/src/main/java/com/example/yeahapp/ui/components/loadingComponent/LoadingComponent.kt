package com.example.yeahapp.ui.components.loadingComponent

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yeahapp.R

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