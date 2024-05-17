package com.example.protapptest.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.compapptest.ui.theme.Primary

@Composable
fun ClickableTextComponent(
    value: String,
    onTextSelected: (String) -> Unit
) {
    val initTxt = "Al continuar aceptas nuestra "
    val privacyPolicyTxt = "Politica de Privacidad"
    val andTxt = " y "
    val termsOfUseTxt = "Terminos de Uso"

    val annotatedString = buildAnnotatedString {
        append(initTxt)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = privacyPolicyTxt, annotation = privacyPolicyTxt)
            append(privacyPolicyTxt)
        }
        append(andTxt)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = termsOfUseTxt, annotation = termsOfUseTxt)
            append(termsOfUseTxt)
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                if ((span.item == termsOfUseTxt) || (span.item == privacyPolicyTxt))
                    onTextSelected(span.item)
            }
        }
    )
}