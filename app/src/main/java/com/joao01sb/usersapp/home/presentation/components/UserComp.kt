package com.joao01sb.usersapp.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joao01sb.usersapp.core.domain.model.Address
import com.joao01sb.usersapp.core.domain.model.Company
import com.joao01sb.usersapp.core.domain.model.Geo
import com.joao01sb.usersapp.core.domain.model.User

@Composable
fun UserComp(
    modifier: Modifier = Modifier,
    user: User
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Green)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("name: ${user.name}", color = Color.Black)
            HorizontalDivider()
            Text("phone: ${user.phone}",color = Color.Black)
            HorizontalDivider()
            Text("Email: " + user.email,color = Color.Black)
            HorizontalDivider()
            Row(
            ) {
                Text(
                    "Street: " + user.address.street,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Text("City: "+user.address.city,
                    modifier = Modifier.weight(1f),
                    color = Color.Black)
            }
            HorizontalDivider()
            Text("webSite: " +user.website, color = Color.Black)
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