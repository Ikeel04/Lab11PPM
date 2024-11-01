package com.ordonez.sharedstorage11

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home-screen", "Save Data", Icons.Filled.AddCircle)
    object Profile : Screen("profile-screen", "Show Data", Icons.Filled.Person)
    object Upload : Screen("upload-screen", "Upload Blog", Icons.Filled.Edit)
    object Blog : Screen("blog:screen", "Blog Post", Icons.Filled.Email)
}