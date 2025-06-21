package com.joao01sb.usersapp.core.presentation.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RetryComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun retry_component_displays_message_correctly() {
        val errorMessage = "Error message"

        composeTestRule.setContent {
            Retry(
                modifier = Modifier,
                message = errorMessage,
                onClick = { }
            )
        }
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun retry_component_displays_message_and_calls_onClick() {
        val errorMessage = "Error message"
        var onClickCalled = false

        composeTestRule.setContent {
            Retry(
                modifier = Modifier,
                message = errorMessage,
                onClick = { onClickCalled = true }
            )
        }
        composeTestRule.onNodeWithTag("retry_button").performClick()
        assert(onClickCalled)
    }

}