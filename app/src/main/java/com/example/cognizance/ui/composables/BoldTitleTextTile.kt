package com.example.cognizance.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun BoldTitleTextTile(
    title: String,
    message: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = textStyle.copy(fontWeight = FontWeight.Bold).toSpanStyle()
            ) {
                append(title)
            }
            withStyle(
                textStyle.toSpanStyle()
            ) {
                append(message)
            }
        }
    )
}
