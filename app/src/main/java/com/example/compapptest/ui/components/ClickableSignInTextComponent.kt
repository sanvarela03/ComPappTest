package com.example.protapptest.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compapptest.ui.theme.Primary

@Composable
fun ClickableSignInTextComponent(
    tryingToSignIn: Boolean = true,
    onTextSelected: (String) -> Unit
) {
    val initTxt = if (tryingToSignIn) "¿Ya tienes una cuenta? " else "¿Todavia no tienes una cuenta? "
    val loginTxt = if (tryingToSignIn) "Ingresar" else "Registrarse"

    val annotatedString = buildAnnotatedString {
        append(initTxt)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginTxt, annotation = loginTxt)
            append(loginTxt)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                if ((span.item == loginTxt)) {
                    onTextSelected(span.item)
                }
            }
        }
    )

}