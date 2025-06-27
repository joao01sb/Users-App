package com.joao01sb.usersapp.details.presentation.screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.details.presentation.fake.FakeDetailsViewModel
import com.joao01sb.usersapp.home.presentation.fake.FakeHomeViewModel
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory
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
class DetailsUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val viewModel = FakeDetailsViewModel()

    @Before
    fun setup() {
        stopKoin()

        startKoin {
            modules(
                module {
                    viewModel<FakeDetailsViewModel> { FakeDetailsViewModel() }
                }
            )
        }
        composeTestRule.setContent {
            val uiState by viewModel.stateDetails.collectAsState()
            DetailsUserScreen(
                state = uiState
            )
        }

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verify_init_state_screen_is_loading() {

        viewModel.getUserLoading()

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun verify_init_details_screen() {
        viewModel.getUser()

        val userScreen = MockUserFactory.createUserEntity().toModel()

        composeTestRule.onNodeWithTag("details_screen_header").assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("user_name")
            .assert(hasText(userScreen.name, substring = false, ignoreCase = true))
    }

    @Test
    fun verify_find_user_no_found() {
        composeTestRule.runOnUiThread {
            viewModel.getUserError()
        }
        composeTestRule.onNodeWithTag("back_to_home").assertIsDisplayed()
    }

}