package com.example.protapptest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compapptest.data.local.dto.NavigationItem


@Composable
fun NavigationItemRow(
    item: NavigationItem,
    onNavItemClicked: (NavigationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavItemClicked(item) }
            .padding(all = 20.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.description
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = item.title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
            )
        )
    }
}