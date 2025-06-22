package com.joao01sb.usersapp.details.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joao01sb.usersapp.core.domain.model.Address
import com.joao01sb.usersapp.core.domain.model.Company
import com.joao01sb.usersapp.core.domain.model.Geo
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.details.presentation.components.DetailSection
import com.joao01sb.usersapp.details.presentation.components.InfoItem
import com.joao01sb.usersapp.details.presentation.components.UserDetailHeader
import com.joao01sb.usersapp.details.presentation.state.UiState

@Composable
fun DetailsUserScreen(
    modifier: Modifier = Modifier,
    state: UiState = UiState(),
    onBack: () -> Unit = {}
) {

    when (state.result) {
        is ResultWrapper.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.result.error.message}")
                TextButton(onBack) {
                    Text(text = "Back")
                }
            }
        }

        is ResultWrapper.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ResultWrapper.Success<*> -> {
            val user = state.result.result as User
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Green)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            UserDetailHeader(user = user)
                            DetailSection(title = "Contato") {
                                InfoItem(icon = Icons.Outlined.Email, text = user.email)
                                InfoItem(icon = Icons.Outlined.Phone, text = user.phone)
                                InfoItem(icon = Icons.Outlined.Info, text = user.website)
                            }
                            DetailSection(title = "Endereço") {
                                InfoItem(
                                    icon = Icons.Outlined.LocationOn,
                                    text = user.address.street
                                )
                                InfoItem(icon = Icons.Outlined.Info, text = user.address.suite)
                                InfoItem(icon = Icons.Outlined.Info, text = user.address.city)
                            }
                            DetailSection(title = "Empresa") {
                                InfoItem(icon = Icons.Outlined.Info, text = user.company.name)
                                InfoItem(
                                    icon = Icons.Outlined.Info,
                                    text = user.company.catchPhrase
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsUserScreen(
        modifier = Modifier.fillMaxWidth(),
        state = UiState(ResultWrapper.Success(User(
            id = 1,
            name = "João Silva",
            username = "joaosilva",
            email = "joao.silva@example.com",
            address = Address(
                street = "Rua das Flores",
                suite = "Apto 123",
                city = "Curitiba",
                zipcode = "80010-000",
                geo = Geo(
                    lat = "156",
                    lng = "154"
                )
            ),
            phone = "(41) 99999-8888",
            website = "http://joaosilva.com",
            company = Company(
                name = "Silva & Cia",
                catchPhrase = "Negócios que transformam",
                bs = "tecnologia, inovação, parceria"
            )
        )))
    )

}
