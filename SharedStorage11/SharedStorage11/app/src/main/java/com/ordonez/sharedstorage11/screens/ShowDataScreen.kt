package com.ordonez.sharedstorage11.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ordonez.sharedstorage11.data.UserData
import com.ordonez.sharedstorage11.data.UserDataStore
import kotlinx.coroutines.launch

@Composable
fun ShowDataScreen(userDataStore: UserDataStore) {
    // Remember a coroutine scope to handle launching coroutines in the UI
    val coroutineScope = rememberCoroutineScope()

    // State to hold the user data
    var userData by remember { mutableStateOf(UserData("", "", "", "")) }

    // Load data from DataStore
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            userDataStore.userData.collect {
                userData = it
            }
        }
    }

    // Display user data
    UserInfoDisplay(userData)
}

@Composable
fun UserInfoDisplay(userData: UserData) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "User Information", style = MaterialTheme.typography.headlineSmall)

        Text(text = "First Name: ${userData.firstName}")
        Text(text = "Last Name: ${userData.lastName}")
        Text(text = "Birth Date: ${userData.birthDate}")
        Text(text = "Email: ${userData.email}")
    }
}

