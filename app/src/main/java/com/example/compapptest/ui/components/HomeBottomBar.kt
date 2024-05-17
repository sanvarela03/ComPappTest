package com.example.compapptest.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.compapptest.data.local.dto.NavigationItem

@Composable
fun HomeBottomBar(
    navigationItems: List<NavigationItem>,
    currentDestination: NavDestination?,
    navigateTo: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
            .height(70.dp),
    ) {
        navigationItems.forEach { navItem: NavigationItem ->
            val selected = currentDestination?.hierarchy?.any { it.route == navItem.itemId } == true

            NavigationBarItem(
                selected = selected,
                onClick = { navigateTo(navItem.itemId) },
                icon = {
                    val icon = if (selected) {
                        navItem.selectedIcon
                    } else {
                        navItem.unselectedIcon
                    }

                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            modifier = Modifier.size(16.dp),
                            contentDescription = ""
                        )
                    }
                },
                label = {
                    Text(
                        text = navItem.title
                    )
                }
            )


        }
    }
}