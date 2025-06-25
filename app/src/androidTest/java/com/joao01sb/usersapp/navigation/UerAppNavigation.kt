package com.joao01sb.usersapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.details.presentation.fake.FakeDetailsViewModel
import com.joao01sb.usersapp.details.presentation.screen.DetailsUserScreen
import com.joao01sb.usersapp.home.presentation.fake.FakeHomeViewModel
import com.joao01sb.usersapp.home.presentation.screen.HomeScreen
import com.joao01sb.usersapp.ui.navigation.destinations.DetailsScreen
import com.joao01sb.usersapp.ui.navigation.destinations.HomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UerAppNavigation {


    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    val viewModel = FakeHomeViewModel()

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavHost(
                navController = navController,
                startDestination = HomeScreen()
            ) {
                composable<HomeScreen> {

                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    HomeScreen(
                        uiState = state,
                        onRetry = viewModel::refresh,
                        onClickUser = { id ->
                            navController.navigate(DetailsScreen(id))
                        }
                    )
                }
                composable<DetailsScreen> { backStackEntry ->
                    val viewModelDetails = FakeDetailsViewModel()
                    val stateDetails by viewModelDetails.stateDetails.collectAsStateWithLifecycle()
                    DetailsUserScreen(
                        state = stateDetails,
                        onBack = navController::popBackStack
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun homeScreen_verify_click_user_navigate_to_detail() {
        composeTestRule.runOnUiThread {
            viewModel.loadUsers()
        }

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("user_name"),
            timeoutMillis = 3000L
        )
        composeTestRule.onAllNodesWithTag("user_button").onFirst().performClick()

        composeTestRule.onNodeWithTag("details_screen_header").assertIsDisplayed()
    }

}