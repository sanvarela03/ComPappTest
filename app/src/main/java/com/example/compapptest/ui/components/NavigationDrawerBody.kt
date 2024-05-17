package com.example.protapptest.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compapptest.data.local.dto.NavigationItem


@Composable
fun NavigationDrawerBody(
    navigationItems: List<NavigationItem>,
    onNavItemClicked: (NavigationItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(navigationItems) {
            NavigationItemRow(item = it, onNavItemClicked)
        }
    }
}