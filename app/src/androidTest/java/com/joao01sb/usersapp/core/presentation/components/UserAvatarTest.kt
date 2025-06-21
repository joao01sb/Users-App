package com.joao01sb.usersapp.core.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.core.utils.getInitials
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserAvatarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyUserAvatarName() {
        val name = "John Doe"

        composeTestRule.setContent {
            UserAvatar(name = name)
        }

        composeTestRule.onNodeWithText(getInitials(name)).assertIsDisplayed()
    }

}