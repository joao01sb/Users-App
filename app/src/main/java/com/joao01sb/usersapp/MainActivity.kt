package com.joao01sb.usersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.joao01sb.usersapp.ui.navigation.NavApp
import com.joao01sb.usersapp.ui.theme.UsersAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsersAppTheme {
                val rememberNavContoller = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavApp(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavContoller
                    )
                }
            }
        }
    }
}
