package com.joao01sb.usersapp.details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.presentation.components.UserAvatar
import com.joao01sb.usersapp.ui.theme.BlackColor
import com.joao01sb.usersapp.ui.theme.GrayMediumColor

@Composable
fun UserDetailHeader(
    user: User
) {
    Column(
        modifier = Modifier.testTag("details_screen_header"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar(
            name = user.name,
            size = 100.dp,
            backgroundColor = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.testTag("user_name"),
            text = user.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BlackColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "@${user.username}",
            fontSize = 16.sp,
            color = GrayMediumColor
        )
    }
}
