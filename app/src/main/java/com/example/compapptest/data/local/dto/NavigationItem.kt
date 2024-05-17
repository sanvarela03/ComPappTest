package com.example.compapptest.data.local.dto

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val description: String,
    val itemId: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
)
