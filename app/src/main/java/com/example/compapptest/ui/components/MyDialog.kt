package com.example.compapptest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyDialog(
    tittle: String,
    text: String,
    show: Boolean,
    onDismissTxt: String = "Rechazar",
    onConfirmTxt: String = "Confirmar",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
//            dismissButton = {
//                TextButton(onClick = { onDismiss() }) {
//                    Text(text = onDismissTxt)
//                }
//            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(onClick = { onConfirm() }) {
                        Text(text = onConfirmTxt)
                    }
                }
            },
            title = { Text(text = tittle) },
            text = { Text(text = text) }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MyDialogPreview() {
    MyDialog(
        tittle = "TITULO",
        text = "TEXTASJFKLDÑSAFA",
        show = true,
        onDismiss = {},
        onConfirm = {},
    )
}
