package com.ordonez.sharedstorage11.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ordonez.sharedstorage11.data.UserDataStore
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun SaveDataScreen(userDataStore: UserDataStore) {
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    // State variables for form inputs
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val nationalities = listOf("Guatemala", "Mexico", "USA", "Canada", "Other")


    // Load data if exists
    LaunchedEffect(Unit) {
        userDataStore.userData.collect { userData ->
            firstName = userData.firstName
            lastName = userData.lastName
            birthDate = userData.birthDate
            email = userData.email
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(birthDate) { selectedDate ->
            birthDate = selectedDate
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("E-mail")},
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                userDataStore.saveUserData(
                    firstName,
                    lastName,
                    birthDate,
                    email,
                )
            }

            showDialog = true

        }) {
            Text("Save")
        }
    }

    // Dialog to indicate success
    if (showDialog) {
        ConfirmationDialog(
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun DatePickerField(value: String, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        }, year, month, day
    )

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text("Birth Date") },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            }
        }
    )
}

@Composable
fun ConfirmationDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = {
            Text(text = "Success")
        },
        text = {
            Text("Your data has been saved successfully.")
        }
    )
}
