package com.joao01sb.usersapp.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.ui.navigation.destinations.DetailsScreen
import com.joao01sb.usersapp.ui.navigation.destinations.HomeScreen
import com.joao01sb.usersapp.details.presentation.screen.DetailsUserScreen
import com.joao01sb.usersapp.details.presentation.viewmodel.DetailsViewModel
import com.joao01sb.usersapp.home.presentation.screen.HomeScreen
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavApp(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen()
    ) {
        composable<HomeScreen> {
            val viewModel: HomeViewModel = koinViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.uiEvents.collect { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            Toast.makeText(
                                navController.context,
                                event.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            HomeScreen(
                uiState = state,
                onRetry = viewModel::refresh,
                onClickUser = { id ->
                    navController.navigate(DetailsScreen(id))
                }
            )
        }
        composable<DetailsScreen> { backStackEntry ->
            val viewModelDetails: DetailsViewModel = koinViewModel()
            LaunchedEffect(Unit) {
                viewModelDetails.getUser()
            }
            val stateDetails by viewModelDetails.stateDetails.collectAsStateWithLifecycle()
            DetailsUserScreen(
                state = stateDetails,
                onBack = navController::popBackStack
            )

        }
    }
}