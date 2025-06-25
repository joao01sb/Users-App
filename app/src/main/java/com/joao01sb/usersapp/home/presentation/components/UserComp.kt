package com.joao01sb.usersapp.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joao01sb.usersapp.core.domain.model.Address
import com.joao01sb.usersapp.core.domain.model.Company
import com.joao01sb.usersapp.core.domain.model.Geo
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.presentation.components.UserAvatar

@Composable
fun UserComp(
    modifier: Modifier = Modifier,
    user: User,
    onClick: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier.padding(all = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Green)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                name = user.name,
                modifier = Modifier.padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .testTag("user_name"),
                    text = user.name,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = user.email,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
            IconButton(
                modifier = Modifier.testTag("user_button"),
                onClick = { onClick(user.id) },
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    "",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun UserCompPreview() {
    val userMock = User(
        id = 1,
        name = "Joao",
        username = "Joao01sb",
        email = "neetjoao@gmail.com",
        address = Address(
            street = "Rua github",
            suite = "256",
            city = "São Paulo",
            zipcode = "00000-000",
            geo = Geo(
                "-156",
                "156"
            ),
        ),
        phone = "11954487109",
        website = "www.github.com/joao01sb",
        company = Company(
            name = "Joao01sb-X",
            catchPhrase = "null",
            bs = "x/x"
        )
    )
    UserComp(user = userMock)
}
