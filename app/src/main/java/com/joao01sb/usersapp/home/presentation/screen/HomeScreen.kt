package com.joao01sb.usersapp.home.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joao01sb.usersapp.core.presentation.components.Retry
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.presentation.components.UserComp
import com.joao01sb.usersapp.home.presentation.state.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState = UiState(),
    onRetry: () -> Unit = {},
    onClickUser: (Int) -> Unit = {}
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.isErro?.first == true) {
            Retry(message = uiState.isErro.second, onClick = onRetry)
        } else {
            Text("All users", fontWeight = FontWeight.W600)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.users) {
                    UserComp(
                        modifier = Modifier.fillMaxWidth(),
                        user = it,
                        onClick = onClickUser
                    )
                }
            }
        }
    }
}