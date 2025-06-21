package com.joao01sb.usersapp.home.presentation.screen

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import com.joao01sb.usersapp.home.presentation.state.UiState
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


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
        val users = MockUserFactory.createUserListEntity().toModelForEntenty()
        val uiState = UiState(
            isLoading = false,
            users = users
        )
        composeTestRule.setContent {
            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = { },
                onClickUser = {}
            )
        }
        composeTestRule
            .onAllNodesWithTag("user_name")
            .assertCountEquals(users.size)

    }

    @Test
    fun homeScreen_verify_error_message() {
        val uiState = UiState(
            isErro = Pair(true, "Erro ao carregar dados")
        )
        composeTestRule.setContent {
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
    fun homeScreen_verify_retry_action() {
        val uiState = UiState(
            isErro = Pair(true, "Erro ao carregar dados")
        )

        var retryIsClicked = false

        composeTestRule.setContent {
            HomeScreen(
                modifier = Modifier,
                uiState = uiState,
                onRetry = {
                    retryIsClicked = true
                },
                onClickUser = {}
            )
        }
        composeTestRule.onNodeWithTag("retry_button").assertTextEquals("Retry")
        composeTestRule.onNodeWithTag("retry_button").performClick()

        assert(retryIsClicked)
    }



}