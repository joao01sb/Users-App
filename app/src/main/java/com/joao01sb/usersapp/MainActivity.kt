package com.joao01sb.usersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.ui.theme.UsersAppTheme
import com.joao01sb.usersapp.home.presentation.screen.HomeScreen
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsersAppTheme {
                val viewModel: HomeViewModel = koinViewModel()
                val snackbarHostState = remember { SnackbarHostState() }
                LaunchedEffect(Unit) {
                    viewModel.uiEvents.collect { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(
                                    event.message,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        uiState = uiState,
                        onRetry = {
                            viewModel.refreshData()
                        }
                    )
                }
            }
        }
    }
}
