package com.joao01sb.usersapp.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun Retry(
    modifier: Modifier,
    message: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, color = Color.Green)
        TextButton(
            modifier = Modifier
                .testTag("retry_button"),
            onClick = onClick, border =
                BorderStroke(0.5.dp, color = Color.Green)
        ) {
            Text("Retry", color = Color.Green)
        }
    }
}