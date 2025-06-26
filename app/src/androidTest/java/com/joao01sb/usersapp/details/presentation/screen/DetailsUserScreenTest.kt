package com.joao01sb.usersapp.details.presentation.screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.details.presentation.fake.FakeDetailsViewModel
import com.joao01sb.usersapp.home.presentation.fake.FakeHomeViewModel
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
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verify_init_details_screen() {
        composeTestRule.setContent {
            val viewModel = FakeDetailsViewModel()
            val uiState by viewModel.stateDetails.collectAsState()
            DetailsUserScreen(
                state = uiState
            )
        }
        composeTestRule.onNodeWithTag("details_screen_header").assertIsDisplayed()
    }

}