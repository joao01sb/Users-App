package com.joao01sb.usersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            val viewModel: HomeViewModel = org.koin.androidx.compose.koinViewModel()
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