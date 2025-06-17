package com.joao01sb.usersapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.joao01sb.usersapp.core.navigation.destinations.DetailsScreen
import com.joao01sb.usersapp.core.navigation.destinations.HomeScreen
import com.joao01sb.usersapp.details.presentation.viewmodel.DetailsViewModel
import com.joao01sb.usersapp.home.presentation.screen.HomeScreen
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = state,
                onRetry = viewModel::refreshData,
                onClickUser = { id ->
                    navController.navigate(DetailsScreen(id))
                }
            )
        }
        composable<DetailsScreen> { backStackEntry ->
            val viewModelDetails: DetailsViewModel = koinViewModel()
            val stateDetails by viewModelDetails.stateDetails.collectAsStateWithLifecycle()
            com.joao01sb.usersapp.details.presentation.screen.DetailsScreen(
                state = stateDetails,
                onBack = navController::popBackStack
            )

        }
    }
}