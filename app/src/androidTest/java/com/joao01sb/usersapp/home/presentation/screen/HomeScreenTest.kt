package com.joao01sb.usersapp.home.presentation.screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.home.presentation.fake.FakeHomeViewModel
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.home.presentation.state.UiState
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        stopKoin()

        startKoin {
            modules(
                module {
                    viewModel<FakeHomeViewModel> { FakeHomeViewModel() }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }



    @Test
    fun homeScreen_verify_title() {
        val uiState = UiState(
            isLoading = false,
            users = MockUserFactory.createUserListEntity().toModelForEntenty()
        )
        composeTestRule.setContent {
            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = { },
                onClickUser = {}
            )
        }
        composeTestRule.onNodeWithTag("homescreen_title").assertIsDisplayed()
    }

    @Test
    fun verify_init_screen_is_loading() {
        val uiState = UiState(
            isLoading = true,
            users = listOf()
        )
        composeTestRule.setContent {
            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = { },
                onClickUser = {}
            )
        }
        composeTestRule.onNodeWithTag("progress_indicator").assertIsDisplayed()
    }

    @Test
    fun homeScreen_verify_users_list() {
        composeTestRule.setContent {

            val viewModel = FakeHomeViewModel()

            viewModel.loadUsers()

            val uiState = viewModel.uiState.value

            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = { },
                onClickUser = {}
            )
        }
        composeTestRule
            .onAllNodesWithTag("user_name")
            .assertCountEquals(MockUserFactory.createUserListEntity().size)

    }

    @Test
    fun homeScreen_verify_error_message() {
        composeTestRule.setContent {

            val viewModel = FakeHomeViewModel()

            viewModel.loadUsersError()

            val uiState = viewModel.uiState.value

            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = { },
                onClickUser = {}
            )
        }
        composeTestRule.onNodeWithTag("retry_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("retry_button").assertTextEquals("Retry")
    }

    @Test
    fun homeScreen_verify_retry_action_simple() {
        val viewModel = FakeHomeViewModel()

        composeTestRule.setContent {
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                onRetry = { viewModel.loadUsers() },
                onClickUser = {}
            )
        }

        composeTestRule.runOnUiThread {
            viewModel.loadUsersError()
        }

        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag("retry_button"),
            timeoutMillis = 3000L
        )
        composeTestRule.onNodeWithTag("retry_button").assertIsDisplayed()

        composeTestRule.onNodeWithTag("retry_button").performClick()

        composeTestRule.waitUntilDoesNotExist(
            hasTestTag("retry_button"),
            timeoutMillis = 5000L
        )

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("user_name"),
            timeoutMillis = 3000L
        )
        composeTestRule
            .onAllNodesWithTag("user_name")
            .assertCountEquals(MockUserFactory.createUserListEntity().size)
    }



}